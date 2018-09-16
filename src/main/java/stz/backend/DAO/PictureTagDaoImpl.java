package stz.backend.DAO;

import stz.backend.entity.Coordinate;
import stz.backend.entity.PictureTag;
import stz.backend.enums.DrawingType;
import stz.backend.service.PictureTagDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PictureTagDaoImpl implements PictureTagDao {
    @Override
    public boolean save(PictureTag pictureTag) {
        try{
            Connection conn = BaseDao.getConnection();
            //delete this item if exists.
            String check = "SELECT * FROM pictureTag WHERE pictureId = ? AND tagId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setString(1, pictureTag.getPictureId());
            checkStmt.setString(2,pictureTag.getTagId());
            ResultSet checkRs = checkStmt.executeQuery();
            if(checkRs.next()){
                String delete = "DELETE FROM pictureTag WHERE pictureId = ? AND tagId = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(delete);
                deleteStmt.setString(1, pictureTag.getPictureId());
                deleteStmt.setString(2,pictureTag.getTagId());
                deleteStmt.executeUpdate();
            }

            String border = "";
            if(pictureTag.getBorder() != null){
                for(int i = 0; i < pictureTag.getBorder().size(); i++){
                    border = border + pictureTag.getBorder().get(i).getX() + "," +
                            pictureTag.getBorder().get(i).getY() + ";";
                }
            }
            String sql = "INSERT INTO pictureTag (pictureId,tagId," +
                    "border,drawingType) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pictureTag.getPictureId());
            stmt.setString(2,pictureTag.getTagId());
            stmt.setString(3,border);
            stmt.setString(4, DrawingType.transToString(pictureTag.getDrawingType()));
            stmt.executeUpdate();

            conn.close();
            stmt.close();
            checkRs.close();
            checkStmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean modify(PictureTag pictureTag) {
        return save(pictureTag);
    }

    @Override
    public boolean delete(String pictureId, String tagId) {
        try{
            Connection conn = BaseDao.getConnection();
            String delete = "DELETE FROM pictureTag WHERE pictureId = ? AND tagId = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(delete);
            deleteStmt.setString(1,pictureId);
            deleteStmt.setString(2,tagId);
            int rs = deleteStmt.executeUpdate();
            if(rs == 0) {
                conn.close();
                deleteStmt.close();
                return false;
            }
            conn.close();
            deleteStmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public PictureTag find(String pictureId, String tagId) {
        PictureTag result = new PictureTag();
        try{
            Connection conn = BaseDao.getConnection();
            String sql = "SELECT * FROM pictureTag WHERE pictureId = ? AND tagId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,pictureId);
            stmt.setString(2,tagId);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                return result;

            //get the coordinates
            ArrayList<Coordinate> coordinates = new ArrayList<>();
            if(rs.getString("border").length() > 0){
                String[] pos = rs.getString("border").split(";");
                for(int i = 0; i < pos.length; i++){
                    String[] temp = pos[i].split(",");
                    Coordinate store = new Coordinate(Integer.parseInt(temp[0]),
                            Integer.parseInt(temp[1]));
                    coordinates.add(store);
                }
            }

            result = new PictureTag(rs.getString("pictureId"),
                    tagId,
                    coordinates,
                    DrawingType.transToDrawingType(rs.getString("drawingType")));
            BaseDao.closeAll(conn, stmt, rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

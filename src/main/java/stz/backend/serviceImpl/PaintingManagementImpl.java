package stz.backend.serviceImpl;

import stz.backend.DAO.BaseDao;
import stz.backend.entity.Coordinate;
import stz.backend.entity.Picture;
import stz.backend.enums.DrawingType;
import stz.backend.service.PaintingManagementService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PaintingManagementImpl implements PaintingManagementService {

    @Override
    public boolean savePaintingResult(Picture picture) {
        try{
            Connection conn = BaseDao.getConnection();
            //delete this item if exists.
            String check = "SELECT * FROM picture WHERE pictureId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setString(1,picture.getPictureId());
            ResultSet checkRs = checkStmt.executeQuery();
            if(checkRs.next()){
                String delete = "DELETE FROM picture WHERE pictureId = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(delete);
                deleteStmt.setString(1,picture.getPictureId());
                deleteStmt.executeUpdate();
            }

            String border = "";
            if(picture.getBorder() != null){
                for(int i = 0; i < picture.getBorder().size(); i++){
                    border = border + picture.getBorder().get(i).getX() + "," +
                            picture.getBorder().get(i).getY() + ";";
                }
            }
            String sql = "INSERT INTO picture (pictureId," +
                    "border,drawingType) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,picture.getPictureId());
            stmt.setString(2,border);
            stmt.setString(3, DrawingType.transToString(picture.getDrawingType()));
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
    public boolean modifyPaintingResult(Picture picture) {
        return savePaintingResult(picture);
    }

    @Override
    public boolean deletePaintingResult(String pictureId) {
        try{
            Connection conn = BaseDao.getConnection();
            String delete = "DELETE FROM picture WHERE pictureId = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(delete);
            deleteStmt.setString(1,pictureId);
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
    public Picture findByPictureId(String pictureId) {
        Picture result = new Picture();
        try{
            Connection conn = BaseDao.getConnection();
            String sql = "SELECT * FROM picture WHERE pictureId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,pictureId);
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

            result = new Picture(rs.getString("pictureId"),
                    coordinates,
                    DrawingType.transToDrawingType(rs.getString("drawingType")));
            BaseDao.closeAll(conn, stmt, rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
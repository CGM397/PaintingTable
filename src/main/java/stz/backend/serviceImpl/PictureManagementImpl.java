package stz.backend.serviceImpl;

import stz.backend.DAO.BaseDao;
import stz.backend.entity.Coordinate;
import stz.backend.entity.Picture;
import stz.backend.enums.DrawingType;
import stz.backend.service.PictureManagementService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PictureManagementImpl implements PictureManagementService {
    @Override
    public boolean savePicture(Picture picture) {
        try{
            Connection conn = BaseDao.getConnection();
            //delete this item if exists.
            String check = "SELECT * FROM picture WHERE pictureId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setString(1, picture.getPictureId());
            ResultSet checkRs = checkStmt.executeQuery();
            if(checkRs.next()){
                String delete = "DELETE FROM picture WHERE pictureId = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(delete);
                deleteStmt.setString(1, picture.getPictureId());
                deleteStmt.executeUpdate();
            }

            String allPoints = "";
            if(picture.getAllPoints() != null){
                for(int i = 0; i < picture.getAllPoints().size(); i++){
                    allPoints  += picture.getAllPoints().get(i).getX() + "," +
                            picture.getAllPoints().get(i).getY() + ";";
                }
            }
            String tags = "";
            if(picture.getTags() != null){
                for(int i = 0; i < picture.getTags().size(); i++){
                    tags += picture.getTags().get(i) + ";";
                }
            }

            String sql = "INSERT INTO picture (pictureId," +
                    "allPoints,tags) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, picture.getPictureId());
            stmt.setString(2,allPoints);
            stmt.setString(3,tags);
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
    public boolean modifyPicture(Picture picture) {
        return savePicture(picture);
    }

    @Override
    public boolean deletePicture(String pictureId) {
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
    public Picture findPicture(String pictureId) {
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
            ArrayList<Coordinate> allPoints = new ArrayList<>();
            if(rs.getString("allPoints").length() > 0){
                String[] pos = rs.getString("allPoints").split(";");
                for(int i = 0; i < pos.length; i++){
                    String[] temp = pos[i].split(",");
                    Coordinate store = new Coordinate(Integer.parseInt(temp[0]),
                            Integer.parseInt(temp[1]));
                    allPoints.add(store);
                }
            }

            ArrayList<String> tagId = new ArrayList<>();
            if(rs.getString("tags").length() > 0){
                String[] ids = rs.getString("tags").split(";");
                for(int i = 0; i < ids.length; i++)
                    tagId.add(ids[i]);
            }
            result = new Picture(rs.getString("pictureId"),
                    allPoints, tagId);
            BaseDao.closeAll(conn, stmt, rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<String> showAllPictures() {
        ArrayList<String> res = new ArrayList<>();

        try{
            Connection conn = BaseDao.getConnection();
            String sql = "SELECT pictureId FROM picture";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                res.add(rs.getString("pictureId"));
            }
            BaseDao.closeAll(conn, stmt, rs);
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}

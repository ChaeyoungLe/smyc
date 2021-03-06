package kh.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.spring.dto.DressDTO;
import kh.spring.dto.DressImgDTO;

@Repository
public class DressDAO {

	@Autowired
	private SqlSessionTemplate sst;
	
	// 옷 등록
	public int insert(DressDTO dto) {
		Map<String, Object> parm = new HashMap<>();
		parm.put("c_no",dto.getC_no());
		parm.put("email",dto.getEmail());
		parm.put("name",dto.getName());
		parm.put("category",dto.getCategory());
		parm.put("pub",dto.getPub());
		parm.put("season",dto.getSeason());
		parm.put("memo",dto.getMemo());
		parm.put("price",dto.getPrice());
		parm.put("buy_date",dto.getBuy_date());
		
		return sst.insert("Dress.insert", parm);
	}
	// 옷 정보 오름차순 가져오기
	public List<DressDTO> selectNo() {
		return sst.selectList("Dress.selectDressNo");
	}
	// 선택한 옷 정보 가져오기
	public DressDTO selectDress(int no) {
		return sst.selectOne("Dress.selectDressInfo", no);
	}
	// 선택한 옷장 옷 정보 가져오기
	public List<DressDTO> selectByCloset(int c_no) {
		return sst.selectList("Dress.selectByCloset", c_no);
	}
	// 사용자가 선택한 옷장 카테고리 별 옷 정보 가져오기
	public List<DressDTO> selectByCategory(String email, String category, int c_no) {
		Map<String, Object> parm = new HashMap<>();
		parm.put("category",category);
		parm.put("c_no",c_no);
		parm.put("email",email);
		
		return sst.selectList("Dress.selectByCategory", parm);
	}
	// 옷 정보 삭제
	public int delete(int no) {
		return sst.delete("Dress.delete",no);
	}
	// 옷장 삭제 시 해당 옷장 옷 삭제
	public int deleteByCloset(int c_no) {
		return sst.delete("Dress.deleteByCloset", c_no);
	}
	// 옷 정보 수정
	public int update(DressDTO dto) {
		Map<String, Object> parm = new HashMap<>();
		parm.put("c_no",dto.getC_no());
		parm.put("name",dto.getName());
		parm.put("category",dto.getCategory());
		parm.put("pub",dto.getPub());
		parm.put("season",dto.getSeason());
		parm.put("memo",dto.getMemo());
		parm.put("price",dto.getPrice());
		parm.put("buy_date",dto.getBuy_date());
		parm.put("no",dto.getNo());
		
		return sst.insert("Dress.update", parm);
	}
	// 이미지 경로를 통해서 옷 상세정보 가져오기
	public DressDTO pathDetailDress(String path) {
		return sst.selectOne("Dress.pathDetailDress", path);
	}
}

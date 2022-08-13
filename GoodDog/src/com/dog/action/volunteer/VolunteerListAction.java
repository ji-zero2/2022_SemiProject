package com.dog.action.volunteer;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dog.action.Action;
import com.dog.command.Criteria;
import com.dog.service.volunteer.VolunteerService;

public class VolunteerListAction implements Action {

	private VolunteerService service;
	public void setVolunteerService(VolunteerService service) {
		this.service = service;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/volunteer/volunteerList";
		
		String pageParam = request.getParameter("page");
		String perPageNumParam = request.getParameter("perPageNum");
		String keyword = request.getParameter("keyword");
		String searchType = request.getParameter("searchType");		
		
		boolean criFlag = true;
		
		criFlag = criFlag && pageParam !=null
				          && !pageParam.isEmpty()
				          && perPageNumParam !=null
				          && !perPageNumParam.isEmpty();
		
		Criteria cri = new Criteria();
		
		
		if(criFlag) {
			try {
				cri.setPage(Integer.parseInt(pageParam));
				cri.setPerPageNum(Integer.parseInt(perPageNumParam));
				cri.setKeyword(keyword);
				cri.setSearchType(searchType);
				
			}catch(Exception e) {
				response.sendError(response.SC_BAD_REQUEST);
				return null;
			}
		}			
		
		try {
			Map<String,Object> dataMap = service.getVolWantMemberList(cri);
			request.setAttribute("dataMap", dataMap);
		} catch (SQLException e) {
			e.printStackTrace();
			url = "/error/500";
		}
		
	
		return url;
	}

}

/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_view.html")
public class CreateViewController extends Controller<CreateViewBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(CreateViewBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(CreateViewBean bean, View view) throws Exception {
		StructureLogic structureLogic = new StructureLogic();
		if (structureLogic.isTableExisted(bean.getView_name())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_VIEW_ALREADY_EXISTED);
			view.setToken(super.encode(jsonObject));
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_VIEW_LIST);
			return;
		}

		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_VIEW);
	}
}
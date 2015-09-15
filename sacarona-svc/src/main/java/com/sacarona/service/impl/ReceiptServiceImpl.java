package com.sacarona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sacarona.common.svc.io.ServiceCollectionResponse;
import com.sacarona.common.svc.io.ServiceRequest;
import com.sacarona.dao.ReceiptDAO;
import com.sacarona.dao.UserDAO;
import com.sacarona.model.dealing.Receipt;
import com.sacarona.model.user.User;
import com.sacarona.service.ReceiptService;

@Service
public class ReceiptServiceImpl implements ReceiptService {
	@Autowired private ReceiptDAO receiptDAO;
	
	@Autowired private UserDAO userDAO;

	@Transactional
	public Receipt insert(Receipt receipt) {
		return receiptDAO.insert(receipt);
	}

	@Transactional
	public ServiceCollectionResponse<Receipt> findByUser(ServiceRequest<Receipt> request) {
		ServiceCollectionResponse<Receipt> result = receiptDAO.findByUser(request);
		for (Receipt receipt : result.getDataList()) {
			if (receipt.getOtherUserId() != null)
				receipt.setOtherUser(userDAO.findById(User.class, receipt.getOtherUserId()));
		}
		return result;
	}

}

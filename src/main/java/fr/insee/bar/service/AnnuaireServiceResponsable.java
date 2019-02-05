package fr.insee.bar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.insee.bar.dao.EmployeDao;
import fr.insee.bar.model.Employe;

@Profile("responsable")
@Component
public class AnnuaireServiceResponsable implements AnnuaireService {

	@Autowired
	private EmployeDao employeDao;

	@Override
	public Employe lookup() {
		return employeDao.find(Short.valueOf("3")).get();
	}

}

package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import JobPortal.model.Company;

/**
 * Created by ipshamohanty on 5/1/17.
 */
@Transactional
public interface CompanyDao extends CrudRepository<Company, Integer> {


    public Company findBycompanyId(int companyId);



}
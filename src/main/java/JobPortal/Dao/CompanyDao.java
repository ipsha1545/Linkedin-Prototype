package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import JobPortal.model.Company;

import java.util.List;

/**
 * Created by ipshamohanty on 5/1/17.
 */
@Transactional
public interface CompanyDao extends CrudRepository<Company, Integer> {


    public Company findByCompanyId(int companyId);

    @Query(value = "select * from jobportal.company where companyemail = ?", nativeQuery = true)
    public Company findByCompanyEmail(String companyemail);

    @Query(value = "select distinct companyname from jobportal.company", nativeQuery = true)
    List<String> getAllCompanies();

}

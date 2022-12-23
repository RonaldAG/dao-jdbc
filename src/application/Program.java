package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("======= teste 1 ========");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("======= teste 2 ========");
		List<Seller> listSeller = sellerDao.findByDepartment(new Department(2, null));
		listSeller.forEach(x -> System.out.println(x));
		
		System.out.println("======= teste 3 ========");
		List<Seller> findAll = sellerDao.findAll();
		findAll.forEach(x -> System.out.println(x));
		
	}

}

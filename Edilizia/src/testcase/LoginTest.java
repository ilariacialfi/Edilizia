package testcase;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import control.LoginController;

public class LoginTest {

	@Test
	public void testDoAccess() throws ClassNotFoundException, SQLException {
		assertFalse(LoginController.accedi("id", "pass"));
		assertTrue(LoginController.accedi("11", "01"));
	}

}

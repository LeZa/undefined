package com.build.api.api;

import org.apache.xerces.impl.dv.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;



public class LdapHelper {

	private static DirContext ctx;

	public static DirContext getCtx() {
		// if (ctx != null ) {
		// return ctx;
		// }
		String account = "Manager"; // binddn
		String password = "pwd"; // bindpwd
		String root = "dc=scut,dc=edu,dc=cn"; // root
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/" + root);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=" + account);
		env.put(Context.SECURITY_CREDENTIALS, password);
		try {
			// ����ldap
			ctx = new InitialDirContext(env);
			System.out.println("��֤�ɹ�");
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("��֤ʧ��");
		} catch (Exception e) {
			System.out.println("��֤���?");
			e.printStackTrace();
		}
		return ctx;
	}

	public static void closeCtx() {
		try {
			ctx.close();
		} catch (NamingException ex) {
			Logger.getLogger(LdapHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {

		// MessageDigest �ṩ����ϢժҪ�㷨���� MD5 �� SHA���Ĺ��ܣ�����LDAPʹ�õ���SHA-1
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// ȡ�������ַ�
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// ����BASE64
		byte[] ldappwbyte = Base64.decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// ǰ20λ��SHA-1���ܶΣ�20λ�����������ʱ���������
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}

		// ���û������������ӵ�ժҪ������Ϣ
		md.update(inputpw.getBytes());
		// �����������ӵ�ժҪ������Ϣ
		md.update(salt);

		// ��SSHA�ѵ�ǰ�û�������м���
		byte[] inputpwbyte = md.digest();

		// ����У����
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	public static void main(String[] args) {
		getCtx();
	}
}

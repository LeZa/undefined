package com.build.thinkingc.out.self.base.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.build.thinkingc.out.self.base.Entity.User;


public class LdapTest {

	String account = "uid=mdm,ou=Application,ou=People,o=avic-intl.cn,o=isp";
	String password = "avic123!@#";
	String root = "o=avic-intl.cn,o=isp";
	public static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String passwordSql = "123456";
	public Connection conn = null;
	public PreparedStatement pst = null;
	public ResultSet rs = null;

	public static void main(String[] args) {

		LdapTest lt =  new LdapTest();
		lt.test2();
	}


	public void test2() {

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// 必须这样写，无论用什么LDAP服务器。
		env.put(Context.PROVIDER_URL, "ldap://10.19.6.60:1389/");// LDAP服务器的地址:端口。
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, account);
		// 载入登陆帐户和登录密码
		env.put(Context.SECURITY_CREDENTIALS, password);
		try {
//			 String baseDN = "ou=Internal"; 
//		    Name baseName = new LdapName(baseDN);  
			LdapContext ctx = new InitialLdapContext(env, null);// 初始化上下文
			System.out.println("认证成功");// 这里可以改成异常抛出。
			/*
			 * -----------------------------------------------------------------
			 */
//			attrs = ctx.getAttributes(account);
			 String[] attrPersonArray = { "uid", "cn", "mobile","mobile2","departmentNumber",
					 "departmentName","smart-lunid","telephoneNumber","mail","mail2","mail3","customized-telephonenumber2"};
//			 String[] attrDepartArray ={"departmentName","o","smart-parentid","smart-shortname"};
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			constraints.setReturningAttributes(attrPersonArray);  
//			String filter = "(&(objectClass=organization)(smart-status=1))";
			String filter="(&(objectClass=organizationalPerson)(smart-status=1))";
			List<User> userL = new ArrayList<User>();
//			List<Depart> departL =  new ArrayList<Depart>();
			NamingEnumeration<SearchResult>  nSearchResult = ctx.search(root,filter, constraints);
			 SearchResult entry = null;   
			 int i = 0;
			 Calendar cal = Calendar.getInstance();
			 System.out.println("........................?>>>"+cal.getTimeInMillis());
		        for(;nSearchResult.hasMore();){   
		            entry = nSearchResult.next();
		             i = i+1;
		            User user = new User();
//		             Depart dep =  new Depart();
		        Attributes attrs =  entry.getAttributes();
		        for( Enumeration<?> vals = attrs.getAll();vals.hasMoreElements();){
					Object val = vals.nextElement();
					System.out.println(val);
					/*............userId...............*/
					if( String.valueOf(val).indexOf("uid") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setUserId(strArr[1].trim());
					}
					/*............userName...............*/
					if( String.valueOf(val).indexOf("cn") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setUserName(strArr[1].trim());
					}
					/*............deptNo...............*/
					if( String.valueOf(val).indexOf("departmentNumber") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setDeptNo(strArr[1].trim());
					}
					/*............deptName...............*/
					if( String.valueOf(val).indexOf("departmentName") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setDeptName(strArr[1].trim());
					}
					/*............uid...............*/
					if( String.valueOf(val).indexOf("mobile") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setCellPhone(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					
					if( String.valueOf(val).indexOf("mobile2") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setCellPhone2(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					
					if( String.valueOf(val).indexOf("smart-lunid") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setSmartLunid(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					if( String.valueOf(val).indexOf("mail") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setMail(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					if( String.valueOf(val).indexOf("mail2") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setMail2(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					if( String.valueOf(val).indexOf("mail3") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setMail3(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					if( String.valueOf(val).indexOf("telephoneNumber") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setTelePhoneNumber(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					if( String.valueOf(val).indexOf("customized-telephonenumber2") > -1){
						String valStr = String.valueOf(val);
						String [] strArr = valStr.split(":");
						user.setTelePhoneNumber2(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
					}
					
//					if( String.valueOf(val).indexOf("departmentName") > -1){
//						String valStr = String.valueOf(val);
//						String [] strArr = valStr.split(":");
//						dep.setDepartName(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
//					}
//					if( String.valueOf(val).indexOf("o") > -1){
//						String valStr = String.valueOf(val);
//						String [] strArr = valStr.split(":");
//						dep.setDepartNo(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
//					}
//					if( String.valueOf(val).indexOf("smart-parentid") > -1){
//						String valStr = String.valueOf(val);
//						String [] strArr = valStr.split(":");
//						dep.setUpDepartNo(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
//					}
//					if( String.valueOf(val).indexOf("smart-shortname") > -1){
//						String valStr = String.valueOf(val);
//						String [] strArr = valStr.split(":");
//						dep.setDepartSName(strArr[1] == null || strArr[1].equals("")?"123456789":strArr[1].trim());
//					}
				}
		        
		        user.setId(UUID.randomUUID().toString());
//		        dep.setId(UUID.randomUUID().toString());
			    	System.out.println("-----------------------------------------------------------------------");
			        userL.add(user);
//			        departL.add(dep);
		        }
		        
		        System.out.println(userL.size()+"........................................................");
		        cal = Calendar.getInstance();
		        System.out.println("........................?>>>"+cal.getTimeInMillis());
		        Class.forName(name);// 指定连接类型
				conn = DriverManager.getConnection(url, user, passwordSql);// 获取连接
				long dateL = cal.getTimeInMillis();
				for(User u:userL){
					String sqlI = "insert into user(id,userId,userName,cellPhone,cellphone2,token,createDate,lastLDate,deptNo,deptName,smartLunid,mail,mail2,mail3,telephoneNumber,telephoneNumber2) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sqlI);
					pst.setString(1, u.getId());
					pst.setString(2,u.getUserId());
					pst.setString(3, u.getUserName());
					pst.setString(4, u.getCellPhone()==null||u.getCellPhone().equals("")||u.getCellPhone().equals("null")?"123456":u.getCellPhone());
					pst.setString(5, u.getCellPhone2()==null||u.getCellPhone2().equals("")||u.getCellPhone2().equals("null")?"123456":u.getCellPhone2());
					pst.setString(6, "123456");
					pst.setLong(7, dateL);
					pst.setLong(8, dateL);
					pst.setString(9, u.getDeptNo() == null?"123456":u.getDeptNo());
					pst.setString(10, u.getDeptName() == null?"中航国际":u.getDeptName());
					pst.setString(11, u.getSmartLunid() == null?"H110":u.getSmartLunid());
					pst.setString(12, u.getMail() == null?"@163.com":u.getMail());
					pst.setString(13, u.getMail2() == null?"@163.com":u.getMail2());
					pst.setString(14, u.getMail3() == null?"@163.com":u.getMail3());
					pst.setString(15, u.getTelePhoneNumber()==null?"0106201895":u.getTelePhoneNumber());
					pst.setString(16, u.getTelePhoneNumber2()==null?"0106201895":u.getTelePhoneNumber2());
					pst.execute();
				}
//				for(Depart dep:departL){
//					String sqlI = "insert into depart(id,departNo,upDepartNo,departName,departSName) "
//							+ "values(?,?,?,?,?)";
//					pst = conn.prepareStatement(sqlI);
//					pst.setString(1, dep.getId());
//					pst.setString(2, dep.getDepartNo()==null?"":dep.getDepartNo());
//					pst.setString(3, dep.getUpDepartNo()==null?"":dep.getUpDepartNo());
//					pst.setString(4, dep.getDepartName()==null?"":dep.getDepartName());
//					pst.setString(5, dep.getDepartSName()==null?"":dep.getDepartSName());
//					pst.execute();
//				}
				cal = Calendar.getInstance();
				 System.out.println("........................?>>>插入数据执行时间:"+cal.getTimeInMillis());
//			SearchResult  result = nSearchResult.next();
//			Attributes atts = result.getAttributes();
//			for( NamingEnumeration<? extends Attribute> as = atts.getAll(); as.hasMoreElements();){
//				Attribute attr = as.next();
//				for( Enumeration<?> vals = attr.getAll();vals.hasMoreElements();){
//					Object val = vals.nextElement();
//					System.out.println(val);
//				}
//			}
			
//			NamingEnumeration<? extends Attribute> attrEnum = atts.getAll();
//			while(attrEnum.hasMore()){
//				Attribute attr = attrEnum.next();
//				String id = attr.getID();
//				System.out.println(id);
//				NamingEnumeration<?> valueEnum = attr.getAll();
//				while( valueEnum.hasMore()){
//					Object val = valueEnum.next();
//					System.out.println(val);
//				}
//				System.out.println(".......................................................................................................");
//			}
			
			
//			SearchControls constraints = new SearchControls(); 
//			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE); 
//			NamingEnumeration<?> en = ctx.search("", "uid=weifa", constraints);
//			while(en != null && en.hasMoreElements()){
//				Object obj = en.nextElement();
//                if(obj instanceof SearchResult){
//                    SearchResult si = (SearchResult) obj;
//                    System.out.println( si.getNameInNamespace() );
//                    System.out.println( si.getName()  );
//                }
//                else{
//                    System.out.println(obj);
//                }
//			}
			
		
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("认证失败");
		} catch (Exception e) {
			System.out.println("认证出错：" + e);
		}finally{
			if( conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

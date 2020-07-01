
//封装了对用户信息（XML文件）的各种操作
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Users {
	Document document;	//DOM解析XML文件后得到的Document
	NodeList userlist;	//所有user节点的列表
	
	//初始化并解析成DOM树
	Users(){
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db=dbf.newDocumentBuilder();
			document=db.parse("data/users.xml");
			userlist=document.getElementsByTagName("user");
		}catch(ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//更改密码
	public void modifyPass(int id,String pass) {
		pass=MD5.md5(MD5.salt+pass);
		for(int i=0;i<userlist.getLength();i++) {
			Node user=userlist.item(i);
			if(Integer.parseInt(user.getChildNodes().item(0).getTextContent())==id)
			{
				user.getChildNodes().item(2).setTextContent(pass);
				break;
			}
		}
		
		TransformerFactory transformerFactory=TransformerFactory.newInstance();
		try {
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult("data/users.xml"));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	//判断账户是否存在
	public boolean isExist(String account) {
		for(int i=0;i<userlist.getLength();i++) {
			Node user=userlist.item(i);
			if(account.equals(user.getChildNodes().item(1).getTextContent()))
				return true;
		}
		
		return false;
	}
	
	
	//验证账号密码
	public int authentication(String account,String pass) {
		pass=MD5.md5(MD5.salt+pass);
		for(int i=0;i<userlist.getLength();i++) {
			Node user=userlist.item(i);
			if(account.equals(user.getChildNodes().item(1).getTextContent()) && pass.equals(user.getChildNodes().item(2).getTextContent()))
				return Integer.valueOf(user.getChildNodes().item(0).getTextContent());
		}
		
		return 0;
	}
	
	//通过账号取id
	public int getId(String account) {
		for(int i=0;i<userlist.getLength();i++) {
			Node user=userlist.item(i);
			if(account.equals(user.getChildNodes().item(1).getTextContent()))
				return Integer.parseInt(user.getChildNodes().item(0).getTextContent());
		}
		
		return 0;
	}
	

	
	//通过id取账号
	public String getAccount(int id) {
		for(int i=0;i<userlist.getLength();i++) {
			Node user=userlist.item(i);
			if(Integer.parseInt(user.getChildNodes().item(0).getTextContent())==id)
				return user.getChildNodes().item(1).getTextContent();
		}
		
		return "";
	}
	
	//取用户数量
	public int getUserCount() {
		return userlist.getLength();
	}
	

	
	//添加用户
	public void addUser(int id,String account,String pass) {
		pass=MD5.md5(MD5.salt+pass);
		Element element=document.createElement("user");
		
		Element child_element=document.createElement("id");
		child_element.appendChild(document.createTextNode(String.valueOf(id)));
		element.appendChild(child_element);
		
		child_element=document.createElement("account");
		child_element.appendChild(document.createTextNode(account));
		element.appendChild(child_element);
		
		child_element=document.createElement("pass");
		child_element.appendChild(document.createTextNode(pass));
		element.appendChild(child_element);
		

		
		document.getElementsByTagName("users").item(0).appendChild(element);

		TransformerFactory transformerFactory=TransformerFactory.newInstance();
		try {
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult("data/users.xml"));
		} catch (TransformerException e) {
			e.printStackTrace();
		}	
	}
}

//实现md5加密
class MD5 {
	public static String salt="asdfji1SDxx)(^5@!*&^>?|00xxSAD1x";	//硬编码的盐值，防止md5被彩虹表破解
	
	public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
              // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}


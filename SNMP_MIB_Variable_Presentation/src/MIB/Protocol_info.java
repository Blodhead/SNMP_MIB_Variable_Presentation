package MIB;

import java.io.IOException;

import com.ireasoning.protocol.snmp.*;

public class Protocol_info {

	private static final String[] hosts = {	"192.168.10.1",
											"192.168.20.1",
											"192.168.30.1"};
	
	private static final int port = 161;
	
	private static final String read_community = "si2019";
	private static final String write_community = "si2019";
	
	private static final String[] oid = { 	".1.3.6.1.2.1.2.2.1.2", // ifdescr
											".1.3.6.1.2.1.2.2.1.3", // ifType
											".1.3.6.1.2.1.2.2.1.4", // idMTU
											".1.3.6.1.2.1.2.2.1.5", // ifSpeed
											".1.3.6.1.2.1.2.2.1.6", // ifPhysicalAdress
											".1.3.6.1.2.1.2.2.1.7", // ifAdminStatus
											".1.3.6.1.2.1.2.2.1.8"};// ifOperativeStatus
																	// = 7
	
	public String[][] getInfo(int index) throws IOException {
		SnmpTarget target = new SnmpTarget(hosts[index], port, read_community, write_community);		
		SnmpSession session = new SnmpSession(target);
		SnmpVarBind[][] table = new SnmpVarBind[7][];
		
		for(int i = 0; i < 7; i++)
			table[i] = session.snmpGetTableColumn(new SnmpOID(oid[i]));
		
		String[][] result = new String[table[0].length][7];
		 
		for(int i = 0; i < table[0].length; i++) 
			for(int j = 0; j < 7; j++)
				result[i][j] = ""+ table[j][i].getValue();
		
		session.close();
		return result;
	}

	
	public String[] getHosts() {
		return hosts;
	}
	

	public static int getPort() {
		return port;
	}
	
	public static String getReadCommunity() {
		return read_community;
	}

	public static String getWriteCommunity() {
		return write_community;
	}

	public static String[] getOid() {
		return oid;
	}


	public static void main(String args[]) {
		GUI s = new GUI();
		Thread t = new Thread(s);
		t.start();
	}
}

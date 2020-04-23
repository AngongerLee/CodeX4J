package ${rootPackage}.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.croot.framework.communication.dmp.DMPPackage;
<#if func.rsp.hasGroup>
import com.croot.framework.communication.dmp.value.group.${func.rsp.group.name};
</#if>
import com.croot.framework.communication.dmp.value.message.${func.rsp.name};
import com.croot.framework.communication.dmp.value.message.${func.req.name};
import com.croot.framework.database.DBConnection;
import com.croot.framework.database.TransactionManager;
import com.croot.framework.util.ParaChecker;
import com.croot.framework.global.BLException;
/**
 * ${function.FunctionCode}
 * ${function.Description}
 *
 * @author ${global.user}
 *
 */
 <!-- 现有的GroupName为大写开头，因此基本不需要大小写转换-->
public class ${func.name :cap_first}Query extends BankToBankQueryBase{
    public ${func.name :cap_first}Query(DMPPackage dataPack) {
            super(dataPack);
        }

    @Override
    protected void query(TransactionManager transMgr) throws Exception {
        ${func.req.name :cap_first} req = (${func.req.name :cap_first}) dataPack.getResquestMsg();
        ${func.rsp.name :cap_first} ans = new ${func.rsp.name :cap_first}();
        dataPack.setResponseMsg(ans);
    <#if func.rsp.hasGroup>
        ${func.rsp.group.name :cap_first} grp = new ${func.rsp.group.name :cap_first}();
        		ans.set${func.rsp.group.name :cap_first}(grp);
    </#if>
       <#list ${func.req.fields} as field>
        String ${field.name ? uncap_first} = req.get${func.req.field :cap_first}();
            <#if ${field.required}>
                if (ParaChecker.isNull(${field.name ? uncap_first})) {
                    throw new BLException("05010000");
                }
            </#if>
       </#list>
          DBConnection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          try {
               conn = transMgr.getConnection();
               	//TODO 插入SQL
               	pstmt = conn.prepareStatement("");
               	//TODO 设置参数
               	rs = pstmt.executeQuery();
                assembleGroups( <#if func.rsp.hasGroup>grp,</#if> rs);
                } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstmt != null) {
                                pstmt.close();
                            }
                        }
                    }//method end

private void assembleGroups(<#if func.rsp.hasGroup>${func.rsp.group.name :cap_first} grp,</#if> ResultSet rs) throws SQLException {
    int index = 0;
    while(rs.next()){
        <#list ${func.ans.fields} as field>

    }
}//method end
}
}//class end
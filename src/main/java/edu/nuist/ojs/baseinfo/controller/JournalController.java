package edu.nuist.ojs.baseinfo.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nuist.ojs.baseinfo.entity.Journal;
import edu.nuist.ojs.baseinfo.entity.JournalSection;
import edu.nuist.ojs.baseinfo.entity.JournalSectionService;
import edu.nuist.ojs.baseinfo.entity.JournalService;
import edu.nuist.ojs.baseinfo.entity.JournalSetting;
import edu.nuist.ojs.baseinfo.entity.JournalSettingService;
import edu.nuist.ojs.baseinfo.entity.Role;
import edu.nuist.ojs.baseinfo.entity.RoleService;
import edu.nuist.ojs.baseinfo.entity.User;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelation;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelationService;
import edu.nuist.ojs.baseinfo.entity.UserService;
import edu.nuist.ojs.baseinfo.entity.emailTpl.EmailConfigPoint;
import edu.nuist.ojs.baseinfo.entity.emailTpl.EmailConfigPointServer;
import edu.nuist.ojs.baseinfo.entity.emailTpl.EmailTemplate;
import edu.nuist.ojs.baseinfo.entity.emailTpl.EmailTplService;

@RestController
public class JournalController {
    @Autowired
    private JournalService jService;

    @Autowired
    private JournalSettingService journalSettingService;


    @Autowired
    private UserService userService;

    @Autowired
    private JournalSectionService journalSectionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleRelationService userRoleRelationService;


    @RequestMapping("/journal/create")
    public JSONObject save(
        @RequestAttribute String abbr, 
        @RequestAttribute String name, 
        @RequestAttribute String email, 
        @RequestAttribute  long pid){
            JSONObject obj = new JSONObject();

            if( jService.findByAbbreviationAndPid(abbr, pid).size()>0){
                obj.put("flag", "abbrexist");
                return obj; 
            }

            User u = null;
            if( (u = userService.findByPublisherIdAndEmail(pid, email)) == null){
                obj.put("flag", "nouser");
                return obj; 
            }
            Journal j = jService.save( Journal.builder().abbreviation(abbr).title(name).pid(pid).build());

            obj.put("journal", j);
            obj.put("flag", "success"); 
            obj.put("user", u); 

        return obj;
    }

    @RequestMapping("/journal/querybyAbbrLike")
    public List<Journal> findByPid(@RequestAttribute  long pid, @RequestAttribute String abbr){
        return jService.querybyAbbrLike(pid, abbr);
    }

    @RequestMapping("/journal/list")
    public List<Journal> findByPid(@RequestAttribute  long pid){
        return jService.findByPid(pid);
    }

    @RequestMapping("/journal/getById")
    public Journal findById(@RequestAttribute  long jid){
        return jService.findById(jid);
    }

    @RequestMapping("/journal/setting")
    public JournalSetting saveSetting(@RequestAttribute JournalSetting setting){
        return journalSettingService.save(setting);
    }

    @RequestMapping("/journal/getroles")
    public String getRoles(@RequestAttribute long uid){
        List<UserRoleRelation> relations = userRoleRelationService.findByUserId(uid);
        HashMap<String, List<Role>> rst =  new HashMap<>();
        for(UserRoleRelation r :relations){
            long jid = r.getJournalId();
            List<Role> tmp = rst.get(jid + "");
            if( tmp == null ) tmp = new LinkedList<>();
            tmp.add( roleService.findById(r.getRoleId()));
            rst.put(Long.valueOf(jid).toString() , tmp);
        }
        return JSONObject.toJSONString( rst, SerializerFeature.DisableCircularReferenceDetect  );
    }

    @RequestMapping("/journal/chanageOrder")
    public String changeOrder(@RequestAttribute long jid, @RequestAttribute double order){
        jService.updateOrder(jid, order);

        return "";
    }

    @RequestMapping("/journal/getSettingByJidAndConfigPoint")
    public JournalSetting getSettingByJidAndConfigPoint(@RequestAttribute long journalId, @RequestAttribute String configPoint){
        return journalSettingService.findByJournalIdAndConfigPoint(journalId,configPoint);

    }

    @RequestMapping("/journal/getAllSettingByJid")
    public String getSettingByJidAndConfigPoint(@RequestAttribute long journalId){
        return JSONObject.toJSONString( journalSettingService.findByJournalId(journalId));

    }

    @RequestMapping("/journal/section/save")
    public JournalSection getSection(@RequestAttribute JournalSection section){
        return journalSectionService.save(section);   
    }

    @RequestMapping("/journal/section/isSectionAuthorityByAid")
    public JournalSection isSectionAuthorityByAid(@RequestAttribute long aid){
        return journalSectionService.getSectionByAid(aid); 
    }

    @RequestMapping("/journal/section/findById")
    public JournalSection findSectionById(@RequestAttribute long id){
        return journalSectionService.findById(id);
    }

    @RequestMapping("/journal/section/findByGuid")
    public JournalSection findByGuid(@RequestAttribute String guid){
        return journalSectionService.findByGuid(guid);
    }

    @RequestMapping("/journal/section/findByJournalId")
    public String findByJournalId(@RequestAttribute long journalId){
        return JSON.toJSONString(journalSectionService.findByJournalId(journalId));
    }

    @RequestMapping("/journal/section/getSectionByTitleLike")
    public String findBySectionTitle(@RequestAttribute long journalId,@RequestAttribute String title ){
        return JSON.toJSONString(journalSectionService.getSectionByTitleLike(journalId, title));
    }

    @RequestMapping("/journal/section/open")//8.11,open按钮
    public JournalSection openSection(@RequestAttribute long id,@RequestAttribute boolean open){
        JournalSection section = journalSectionService.findById(id);
        if(section!=null) {
            section.setOpen(open);
            return journalSectionService.save(section);
        }
        return null;
    }

    @RequestMapping("/journal/section/order")//8.11,order按钮
    public JournalSection orderSection(@RequestAttribute long id,@RequestAttribute double order){
        JournalSection section = journalSectionService.findById(id);
        if(section!=null) {
            section.setOrder(order);
            return journalSectionService.save(section);
        }
        return null;
    }

    //===========================================================
    // //zhj,8.9,role(base)
    @RequestMapping("/journal/role/save")
    public Role saveRole(@RequestAttribute Role role){
        return roleService.save(role);
    }
    @RequestMapping("/journal/role/findById")
    public Role findRoleById(@RequestAttribute long id){
        return roleService.findById(id);
    }

    @RequestMapping("/journal/role/findByAbbr")
    public Role findRoleById(@RequestAttribute String abbr){
        return roleService.findByAbbr( abbr );
    }
    @RequestMapping("/journal/role/findByJournalId")
    public List<Role> findRoleByJournalId(@RequestAttribute long journalId){
        return roleService.findByJournalId(journalId);
    }

    @RequestMapping("/jouranl/getOriginRole")
    public List<Role> getOriginRole(){
        return roleService.findOriginRole();
    }

    @RequestMapping("/jouranl/findAllRoleForJournal")
    public List<Role> findAllRoleForJournal( @RequestAttribute long jid ){
        return roleService.findAllRoleForJournal(jid);
    }

    @RequestMapping("/journal/getManager")
    public User getManager( @RequestAttribute long jid ){
        return userService.findManagerForJournal(jid);
    }

    @RequestMapping("/journal/getJournalEditorTeamByRole")
    public String getEditorsByJidAndRoleId( @RequestAttribute long jid , @RequestAttribute long rid){
        return JSONObject.toJSONString(userService.findUserByRoleForJournal(jid, rid));
    }

    //=====================================================================
    @RequestMapping("/journal/userRoleRelation/findByJidAndUidAndRid")
    public UserRoleRelation findByJidAndUidAndRid( @RequestAttribute long jid, @RequestAttribute long uid, @RequestAttribute long rid ){
        return userRoleRelationService.findByJidAndUidAndRid(jid, uid, rid);
    }

    //zhj,8.9,userRoleRelation(base)
    @RequestMapping("/journal/userRoleRelation/save")
    public UserRoleRelation saveUserRoleRelation(@RequestAttribute UserRoleRelation userRoleRelation){
        return userRoleRelationService.save(userRoleRelation);
    }
    @RequestMapping("/journal/userRoleRelation/findByUserId")
    public List<UserRoleRelation> findUserRoleRelationByUserId(@RequestAttribute long userId){
        return userRoleRelationService.findByUserId(userId);
    }
    @RequestMapping("/journal/userRoleRelation/findByJournalId")
    public List<UserRoleRelation> findUserRoleRelationByJournalId(@RequestAttribute long journalId){
        return userRoleRelationService.findByJournalId(journalId);
    }

    @RequestMapping("/journal/getAllEditor")
    public List<User> getAllEditorForJournal(@RequestAttribute long jid, @RequestAttribute String email){
        return userService.getAllEditorForJournalAndEmailLike(jid, email);
    }
    //======================================================================
    @RequestMapping("/journal/team/queryByJournalId")
    public List<Object[]> queryTeamByJournalId(@RequestAttribute long jid){
        return userRoleRelationService.queryTeam(jid);
    }

    @RequestMapping("/journal/team/member")
    public List<Object[]>  getTeamMember(@RequestAttribute long jid, @RequestAttribute long rid){
        return userService.getTeamMember(jid,rid);
    }

    @RequestMapping("/journal/team/remove")
    public void removeTeamMember(@RequestAttribute long urrid){
         userRoleRelationService.removeMember(urrid);
    }


    //======== email config===================================================
    @Autowired
    private EmailConfigPointServer ecpServer;

    @Autowired
    private EmailTplService tplService;
     
    @RequestMapping("/journal/emailconfig/")
    public String getEmailConfigForJournal(
        @RequestAttribute long jid,
        @RequestAttribute String configPoint){
            EmailConfigPoint ecp = ecpServer.findByConfigPointAndJid(configPoint, jid);
            return JSONObject.toJSONString(ecp);
    }

    @RequestMapping("/journal/emailconfig/all")
    public String getAllEmailConfigForJournal(
        @RequestAttribute long jid){
            List<EmailConfigPoint> ecp = ecpServer.getAllEmailConfigPointsByJid( jid);
            return JSONObject.toJSONString(ecp);
    }

    @RequestMapping("/journal/emailtpl/getById")
    public String getEmailtpl(
        @RequestAttribute long tid
    ){
        return JSONObject.toJSONString(tplService.findById(tid));
    }

    @RequestMapping("/journal/emailtpl/delyId")
    public String delEmailtpl(
        @RequestAttribute long tid
    ){
        tplService.delById(tid);
        return "";
    }

    @RequestMapping("/journal/emailtpl/update")
    public String updateEmailtpl(
        @RequestAttribute long tid,
        @RequestAttribute String jsondata
    ){
        tplService.update(tid, jsondata);
        return "";
    }

    @RequestMapping("/journal/emailtpl/setDefault")
    public String setEmailDefaultTpl(
        @RequestAttribute long tid
    ){
        tplService.setDefault(tid);
        return "";
    }

    @RequestMapping("/journal/setting/email/setWithEmail")
    public String setWithEmail(
        @RequestAttribute long cid,
        @RequestAttribute boolean flag,
        @RequestAttribute String configPoint,
        @RequestAttribute long jid
    ){
        if(cid==0){
            //如果没有自定义的模板，CID==0,则创建一个配置点，并返回CID
            EmailConfigPoint cp = ecpServer.save(EmailConfigPoint.builder().configPoint(configPoint).jid(jid).build());
            cid = cp.getId();
        }
        //再保存
        ecpServer.setWithEmail(cid, flag);
        return "";
    }

    @RequestMapping("/journal/emailtpl/save")
    public String saveEmailtpl(
        @RequestAttribute long jid,
        @RequestAttribute String  configPoint,
        @RequestAttribute boolean defaultTpl,
        @RequestAttribute String jsonData,
        @RequestAttribute boolean email
    ){
            EmailConfigPoint ecp = ecpServer.findByConfigPointAndJid(configPoint, jid);
            if( ecp == null){
                ecp = ecpServer.save(EmailConfigPoint.builder().configPoint(configPoint).email(email).jid(jid).build());
            }
            EmailTemplate tpl = EmailTemplate.builder()
                                .configPointId(ecp.getId())
                                .defaultTpl(defaultTpl)
                                .jsonData(jsonData)
                                .build();
            tplService.save(tpl);
            return JSONObject.toJSONString(tpl);
    }
}

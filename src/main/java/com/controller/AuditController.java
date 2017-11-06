/*package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fg.smartaudit.dto.AdSearchDto;
import com.fg.smartaudit.dto.AddvAuditReportListDto;
import com.fg.smartaudit.dto.AssetDto;
import com.fg.smartaudit.dto.AuditCommentDto;
import com.fg.smartaudit.dto.AuditDto;
import com.fg.smartaudit.dto.AuditImageDto;
import com.fg.smartaudit.dto.AuditNotificationDto;
import com.fg.smartaudit.dto.AuditRespondentDto;
import com.fg.smartaudit.dto.AuditResultsDto;
import com.fg.smartaudit.dto.CreateAssetAuditDto;
import com.fg.smartaudit.dto.CreateAuditDto;
import com.fg.smartaudit.dto.FindingsListDto;
import com.fg.smartaudit.dto.NameIdDto;
import com.fg.smartaudit.dto.QuestionDto;
import com.fg.smartaudit.dto.SectionDto;
import com.fg.smartaudit.dto.ShareAuditDto;
import com.fg.smartaudit.dto.ShareUserDto;
import com.fg.smartaudit.dto.SubAssetDto;
import com.fg.smartaudit.dto.SummaryDto;
import com.fg.smartaudit.dto.UpdateNameDto;
import com.fg.smartaudit.entity.DownloadOptions;
import com.fg.smartaudit.exception.DataAlreadyExistException;
import com.fg.smartaudit.service.IAssetService;
import com.fg.smartaudit.service.IAuditResultService;
import com.fg.smartaudit.service.IAuditService;
import com.fg.smartaudit.service.IDownloadOptionsService;
import com.fg.smartaudit.service.IFindingsService;
import com.fg.smartaudit.service.IQuestionService;
import com.fg.smartaudit.service.ISectionService;
import com.fg.smartaudit.utility.ApplicationConstants;
import com.fg.smartaudit.utility.AuditStatusEnum;
import com.fg.smartaudit.utility.AuditTypeEnum;
import com.fg.smartaudit.utility.ExtensionMime;
import com.fg.smartaudit.utility.GeneralUtil;
import com.fg.smartaudit.utility.QuestionRespondentsDto;

@Controller
@RequestMapping(value = "/workspace/audit")
public class AuditController {
  private static final int MAXRESULT = 20;
  private static final Logger LOGGER = LogManager.getLogger(AuditController.class);
  private static final String ACTIVE = "active";
  private static final String AUDIT_ACTIVE_CLASS = "audit_active_class";
  private static final String AUDIT_ARCHIVE_CLASS = "audit_archive_class";
  
  private static final String CUSTOMPARENTAUDIT_ACTIVE_CLASS = "customparentaudit_active_class";
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private IAuditService auditService;
  @Autowired
  private IQuestionService questionService;
  @Autowired
  private ISectionService sectionService;
  @Autowired
  private IAssetService assetService;
  @Autowired
  private IDownloadOptionsService downloadOptionService;
  @Autowired
  private IAuditResultService auditResultService;
  @Autowired
  private IFindingsService findingsService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.setAutoGrowCollectionLimit(5000);
  }

  @RequestMapping(value = "/manage", method = RequestMethod.GET)
  public ModelAndView manage(HttpServletRequest request, HttpSession session, @RequestParam(required = false) Integer pageNum,
      @RequestParam(required = false) String auditType, AdSearchDto adSearchDto) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();
   
    if (pageNum == null || pageNum <= 0) {
      pageNum = 1;
    }

    if (StringUtils.isEmpty(auditType)) {
      auditType = "allAudit";
    }


    List<AuditStatusEnum> statusList = new ArrayList<>();
    statusList.add(AuditStatusEnum.NOT_PUBLISH);
    statusList.add(AuditStatusEnum.PUBLISH);
    List<AuditDto> audits = auditService.findAllByStatusIn(statusList, false, false, false, pageNum, MAXRESULT, auditType,false);
    data.put("audits", audits);
    data.put(AUDIT_ACTIVE_CLASS, ACTIVE);
    data.put("pageNum", pageNum);
    data.put("auditType", auditType);
//    String refererHeader = request.getHeader("Referer");
//    if (message != null) {
      if ("success".equals(session.getAttribute("published"))) {
        data.put("message", messageSource.getMessage("audit.publishsuccess", null, Locale.ENGLISH));
        session.removeAttribute("published");
      }  else if ("success".equals(session.getAttribute("addcustomaudit")) ) {
        data.put("message", messageSource.getMessage("addcustomaudit.success", null, Locale.ENGLISH));
        session.removeAttribute("addcustomaudit");
      }else if ("errorArchive".equals(session.getAttribute("error")) ) {
          data.put("error", messageSource.getMessage("archive.error", null, Locale.ENGLISH));
          session.removeAttribute("error");
       }else if ("errorUnArchive".equals(session.getAttribute("error")) ) {
           data.put("error", messageSource.getMessage("unarchive.error", null, Locale.ENGLISH));
           session.removeAttribute("error");
       }
      else if ("stillShared".equals(session.getAttribute("error")) ) {
            data.put("error", messageSource.getMessage("archive.stillShared", null, Locale.ENGLISH));
            session.removeAttribute("error");
          }
//    }
      data.put("archive",false);
    data.put("totalPages", auditService.getCount(MAXRESULT, statusList, auditType,false));
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("manageaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public ModelAndView createGet() {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    CreateAuditDto createAuditDto = new CreateAuditDto();
    data.put("createAuditDto", createAuditDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ModelAndView createPost(@ModelAttribute CreateAuditDto createAuditDto, Errors errors, @RequestParam(required = false) String addMoreQuestion) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {

        auditService.saveAudit(createAuditDto);
        if ("true".equalsIgnoreCase(addMoreQuestion)) {
          createAuditDto.setSection(null);
          String createSuccessMsg = messageSource.getMessage("createaudit.success", null, Locale.ENGLISH);
          modelAndView.setViewName("redirect:/workspace/audit/addquestion/" + createAuditDto.getId() + "?createSuccessMsg=" + createSuccessMsg);
          return modelAndView;

        }

        modelAndView.addObject("success", messageSource.getMessage("createaudit.success", null, Locale.ENGLISH));

      } catch (DataAlreadyExistException e) {
        LOGGER.info("Audit with name : " + createAuditDto.getName() + " is already exist. message is:-", e);
        errors.rejectValue("name", "addaudit.alreadyexist");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView.addObject("error", messageSource.getMessage("createaudit.error", null, Locale.ENGLISH));
      }
    }
    data.put("createAuditDto", createAuditDto);
    data.put("addMoreQuestion", addMoreQuestion);

    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
  public ModelAndView view(HttpSession session, @PathVariable Integer id) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    AuditDto auditDto = auditService.findWithDetailsById(id, true, true, true, false);
    data.put("auditDto", auditDto);
//    String refererHeader = request.getHeader("Referer");

    if ("success".equals(session.getAttribute("published"))) {
      data.put("success", messageSource.getMessage("audit.publishsuccess", null, Locale.ENGLISH));
      session.removeAttribute("published");
    }
    if ("success".equals(session.getAttribute("deleteQuestion")) && refererHeader != null) {
      data.put("success", messageSource.getMessage("deleteQuestion.success", null, Locale.ENGLISH));
      session.removeAttribute("deleteQuestion");
    }
    if ("success".equals(session.getAttribute("editcustomaudit")) ) {
      data.put("success", messageSource.getMessage("editcustomaudit.success", null, Locale.ENGLISH));
      session.removeAttribute("editcustomaudit");
    }

    if ("success".equals(session.getAttribute("convert"))) {
      data.put("success", messageSource.getMessage("audit.convert", null, Locale.ENGLISH));
      session.removeAttribute("convert");
    }
    if ("success".equals(session.getAttribute("convertauditalreadyexist"))) {
      data.put("error", messageSource.getMessage("audit.dataAlreadyExistError", null, Locale.ENGLISH));
      session.removeAttribute("convertauditalreadyexist");
    }
    if(auditDto.isArchive()){
        modelAndView.addObject("isEditable", false);     
    }else{
        modelAndView.addObject("isEditable", true);    
    }
   
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("viewaudit");

    return modelAndView;
  }


  @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
  public ModelAndView publish(HttpSession session, @PathVariable int id, HttpServletRequest request, @RequestParam(required = false) String page, @RequestParam(required = false) String auditType) {
    ModelAndView modelAndView = new ModelAndView();
    auditService.publish(id);
    session.setAttribute("published", "success");
    modelAndView.setViewName("redirect:"+request.getHeader("Referer"));

    return modelAndView;
  }

  @RequestMapping(value = "/addquestion/{auditId}", method = RequestMethod.GET)
  public ModelAndView addquestion(@PathVariable int auditId, @RequestParam(required = false) String createSuccessMsg) throws IllegalAccessException,
      InvocationTargetException {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();

    AuditDto auditDto = auditService.findById(auditId, true, false, false);
    CreateAuditDto createAuditDto = new CreateAuditDto();
    BeanUtils.copyProperties(createAuditDto, auditDto);
    data.put("createAuditDto", createAuditDto);
    data.put("auditNameOld", createAuditDto.getName());
    data.put("section", createAuditDto.getSections());
    data.put("type", "Add");
    data.put("auditType", auditDto.getAuditType());
    if (null != createSuccessMsg) {
      data.put("addMoreQuestion", true);
      data.put("success", createSuccessMsg);
    }
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("addquestion");
    return modelAndView;
  }

  @RequestMapping(value = "/addquestion/{auditId}", method = RequestMethod.POST)
  public ModelAndView addquestionPost(@ModelAttribute CreateAuditDto createAuditDto,@PathVariable int auditId, Errors errors, @RequestParam String auditNameOld,
      @RequestParam String auditInEditMode, @RequestParam(required = false) String addMoreQuestion) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();

    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {
        auditService.addQuestion(createAuditDto, "AddQuestion");
        AuditDto auditDto = auditService.findById(auditId, true, false, false);
        data.put("auditType", auditDto.getAuditType());
//        if ("true".equalsIgnoreCase(addMoreQuestion)) {
            BeanUtils.copyProperties(createAuditDto, auditDto);
          SectionDto section = createAuditDto.getSection();
          List<SectionDto> sections = createAuditDto.getSections();
         if (!sections.contains(section)) {
            sections.add(section);
          }else{
              sections.remove(section);
              sections.add(section);
          }
          createAuditDto.setSection(null);
//        }
        modelAndView.addObject("success", messageSource.getMessage("addquestion.success", null, Locale.ENGLISH));
      } catch (DataAlreadyExistException e) {
        LOGGER.info("Audit with name : " + createAuditDto.getName() + " is already exist. message is:-", e);
        errors.rejectValue("name", "addaudit.alreadyexist");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView.addObject("error", messageSource.getMessage("addquestion.error", null, Locale.ENGLISH));
      }
    }

    data.put("createAuditDto", createAuditDto);
    data.put("auditNameOld", auditNameOld);
    data.put("addMoreQuestion", addMoreQuestion);
    data.put("auditInEditMode", auditInEditMode);
    data.put("type", "Add");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("addquestion");
    return modelAndView;
  }

  @RequestMapping(value = "/editquestion/{auditId}", method = RequestMethod.GET)
  public ModelAndView editQuestion(@PathVariable int auditId, @RequestParam("questionId") long questionId) throws IllegalAccessException,
      InvocationTargetException {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    CreateAuditDto createAuditDto = questionService.editQuestionData(auditId, questionId);
    data.put("createAuditDto", createAuditDto);
    data.put("type", "Edit");
    data.put("auditNameOld", createAuditDto.getName());
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("addquestion");
    return modelAndView;
  }

  @RequestMapping(value = "/editquestion/{auditId}", method = RequestMethod.POST)
  public ModelAndView editQuestionPost(@ModelAttribute CreateAuditDto createAuditDto, Errors errors, @RequestParam String auditNameOld,
      @RequestParam String auditInEditMode) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();

    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {
        auditService.addQuestion(createAuditDto , "editQuestion");
        modelAndView.addObject("success", messageSource.getMessage("editquestion.success", null, Locale.ENGLISH));
      } catch (DataAlreadyExistException e) {
        LOGGER.info("Audit with name : " + createAuditDto.getName() + " is already exist. message is:-", e);
        errors.rejectValue("name", "addaudit.alreadyexist");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView.addObject("error", messageSource.getMessage("editquestion.error", null, Locale.ENGLISH));
      }
    }

    data.put("createAuditDto", createAuditDto);
    data.put("auditNameOld", auditNameOld);
    data.put("auditInEditMode", auditInEditMode);
    data.put("type", "Edit");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("addquestion");
    return modelAndView;
  }


  @RequestMapping(value = "/delete/{auditId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> deleteAuditId(@PathVariable long auditId, @RequestParam Boolean isDeleteAll) {
    Map<String, Object> data = new HashMap<String, Object>();
    try{
    auditService.delete(auditId, isDeleteAll);
    data.put("success", true);
    }catch(NullPointerException | IllegalArgumentException | InvalidDataAccessApiUsageException e){
        LOGGER.error("Error : ",e);
        data.put("success", false);
        if(!isDeleteAll){
        data.put("message","Entity does not exist");
        }
    }
    catch(Exception e){
        data.put("success", false); 
        LOGGER.error("Error : ",e);
    }
    return data;
  }
  
  @RequestMapping(value = "/delete/custom", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> deleteCustomAudit() {
    Map<String, Object> data = new HashMap<String, Object>();
    try{
      List<NameIdDto> findNameIdByAuditType = auditService.findNameIdByAuditType(AuditTypeEnum.CUSTOM);
     if(findNameIdByAuditType != null){
       for (NameIdDto nameIdDto : findNameIdByAuditType) {
         auditService.delete(nameIdDto.getId(), false);
      }
     }
    
    data.put("success", true);
    }catch(NullPointerException | IllegalArgumentException | InvalidDataAccessApiUsageException e){
        LOGGER.error("Error : ",e);
        data.put("success", false);
        
    }
    catch(Exception e){
        data.put("success", false); 
        LOGGER.error("Error : ",e);
    }
    return data;
  }


  @RequestMapping(value = "/delete/{auditId}/{questionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> delete( HttpSession session,@PathVariable long questionId) {
    Map<String, Object> data = new HashMap<String, Object>();
    questionService.delete(questionId);
    session.setAttribute("deleteQuestion", "success");
    data.put("success", true);
    return data;
  }

  @RequestMapping(value = "/share/{auditId}", method = RequestMethod.GET)
  public ModelAndView share(@PathVariable int auditId) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();
    ShareAuditDto shareAuditDto = auditService.getSharing(auditId);
    data.put("auditId", auditId);
    data.put("shareAuditDto", shareAuditDto);
    modelAndView.setViewName("shareAudit");
    modelAndView.addAllObjects(data);
    return modelAndView;
  }

  @RequestMapping(value = "/share/save/{auditId}", method = RequestMethod.POST)
  public ModelAndView sharesave(@PathVariable int auditId, @Valid @ModelAttribute ShareAuditDto shareAuditDto) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();

    auditService.addSharing(auditId, shareAuditDto);
    data.put("success", "shareaudit.success");
    ShareAuditDto sharing = auditService.getSharing(auditId);
    if(shareAuditDto.getStopSharing()==null){
      sharing.setStopSharing(null);
    }
    data.put("shareAuditDto", sharing);
    data.put("auditId", auditId);
    modelAndView.setViewName("shareAudit");
    modelAndView.addAllObjects(data);
    return modelAndView;
  }

  @RequestMapping(value = "/viewresult/{id}", method = RequestMethod.GET)
  public ModelAndView viewResult(@PathVariable int id, @RequestParam(name="shareHistoryId", required=false) Long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    AuditResultsDto auditResultDto = auditResultService.findResultsDetailById(id, shareHistoryId);
//    data.put("auditDto", auditResultDto.getAuditDto());
    data.put("auditResultDto", auditResultDto);

    modelAndView.addAllObjects(data);
    modelAndView.setViewName("viewauditresult");
    return modelAndView;
  }


//  @RequestMapping(value = "/question/viewimage/{questionId}", method = RequestMethod.GET)
//  public ModelAndView viewQuestionImage(@PathVariable long questionId) {
//    ModelAndView modelAndView = new ModelAndView();
//
//    Map<String, Object> data = new HashMap<String, Object>();
//    AuditResultDto auditResultDto = questionService.findImageResultDetailsByQuestionId(questionId);
//    data.put("auditDto", auditResultDto.getAuditDto());
//    data.put("auditResultDto", auditResultDto);
//    data.put("sectionHeaderRequired", true);
//    modelAndView.addAllObjects(data);
//    modelAndView.setViewName("imageResult");
//    return modelAndView;
//  }


//  @RequestMapping(value = "/viewimage/{auditId}", method = RequestMethod.GET)
//  public ModelAndView viewImage(@PathVariable long auditId) {
//    ModelAndView modelAndView = new ModelAndView();
//
//    Map<String, Object> data = new HashMap<String, Object>();
//    AuditResultDto auditResultDto = auditService.findImageResultDetailsById(auditId);
//    data.put("auditResultDto", auditResultDto);
//    modelAndView.addAllObjects(data);
//    modelAndView.setViewName("imageResult");
//    return modelAndView;
//  }

  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<AuditDto> searchAudit(@RequestParam(required = true) String auditType, @RequestParam(required = true) String searchStr,@RequestParam(required = true) boolean isArchive) {
    return auditService.searchAudit(auditType, searchStr,isArchive);
  }


  // ----Methods for Custom Parent Audit----
  
   * author: Ravi Sharma Date: 28/12/2016 This method is to view list of all custom parent audit.
   
  @RequestMapping(value = "/managecustomparent", method = RequestMethod.GET)
  public ModelAndView manage(@RequestParam(required = false) Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();

    if (pageNum == null || pageNum <= 0) {
      pageNum = 1;
    }

    List<AuditDto> audits = auditService.findAllByAuditType(AuditTypeEnum.PARENTCUSTOM, pageNum, MAXRESULT);
    data.put(CUSTOMPARENTAUDIT_ACTIVE_CLASS, ACTIVE);
    data.put("audits", audits);
    data.put("pageNum", pageNum);
    data.put("totalPages", auditService.getParentCustomCount(MAXRESULT));
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("managecustomparentaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/createasset", method = RequestMethod.GET)
  public ModelAndView createCustomGet() {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    List<NameIdDto> audits = auditService.findNameIdByAuditType(AuditTypeEnum.PARENTCUSTOM);
    // data.put("audits", audits);
    CreateAssetAuditDto createAssetAuditDto = new CreateAssetAuditDto();
    createAssetAuditDto.setParentAuditList(audits);
    List<AssetDto> assetdtoList=assetService.findAll();
    data.put("assetdtoList", assetdtoList);
    data.put("createAssetAuditDto", createAssetAuditDto);
    data.put("showSave", true);
    data.put("type", "Add");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createassetaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/createasset", method = RequestMethod.POST)
  public ModelAndView createCustomPost(HttpSession session,@ModelAttribute CreateAssetAuditDto createAssetAuditDto, Errors errors) throws IllegalAccessException, InvocationTargetException {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    String flag = new String();
    try {

      flag = auditService.saveCustomAudit(createAssetAuditDto);
      if ("success".equalsIgnoreCase(flag)) {
        modelAndView.setViewName("redirect:./manage");
        session.setAttribute("addcustomaudit" , "success");
        return modelAndView;
      }
    } catch (DataAlreadyExistException e) {
      LOGGER.info("Audit with name : " + createAssetAuditDto.getName() + " is already exist. message is:-" + e.getMessage(), e);
      errors.rejectValue("name", "addaudit.alreadyexist");
    }
    
    if ("questionRequired".equalsIgnoreCase(flag)) {
      data.put("error", messageSource.getMessage("audit.addQuestion", null, Locale.ENGLISH));
    }
    if (createAssetAuditDto.getParentAudit().getId() != 0) {
      AuditDto auditDto = auditService.findWithDetailsById(createAssetAuditDto.getParentAudit().getId(), true, true, true, false);
      createAssetAuditDto.setParentAudit(auditDto);
    }
    data.put("createAssetAuditDto", createAssetAuditDto);
    data.put("showSave", true);
    List<AssetDto> assetdtoList=assetService.findAll();
    data.put("assetdtoList", assetdtoList);
    data.put("type", "Add");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createassetaudit");
    return modelAndView;
  }
  @RequestMapping(value = "/editasset/{id}", method = RequestMethod.POST)
  public ModelAndView editCustomPost(HttpSession session,@ModelAttribute CreateAssetAuditDto createAssetAuditDto) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
     String message = auditService.saveCustomAuditChanges(createAssetAuditDto);
   
      if("failed".equalsIgnoreCase(message)){
        data.put("error", messageSource.getMessage("editcustomaudit.fail", null, Locale.ENGLISH));
      } else {
        modelAndView.setViewName("redirect:../view/" + createAssetAuditDto.getId());
        session.setAttribute("editcustomaudit", "success");
        return modelAndView;
      }
      
      AuditDto auditDto = auditService.findWithDetailsById(createAssetAuditDto.getParentAudit().getId(), true, true, true, false);
      createAssetAuditDto.setParentAudit(auditDto);
      
    data.put("createAssetAuditDto", createAssetAuditDto);
    data.put("showSave", true);
    data.put("type", "Edit");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createassetaudit");
    return modelAndView;
  }

  @RequestMapping(value = "/getCustomParentDetails/{auditId}", method = RequestMethod.GET)
  public ModelAndView getCustomParentDetails(@PathVariable int auditId) throws IllegalAccessException, InvocationTargetException {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    AuditDto auditDto = auditService.findWithDetailsById(auditId, true, true, true, false);
    CreateAssetAuditDto createAssetAuditDto = new CreateAssetAuditDto();
    createAssetAuditDto.setParentAudit(auditDto);
    List<AssetDto> assetdtoList=assetService.findAll();
    data.put("assetdtoList", assetdtoList);
    data.put("createAssetAuditDto", createAssetAuditDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("sectionandquestion");
    return modelAndView;
  }
  
  @RequestMapping(value = "/editasset/{id}", method = RequestMethod.GET)
  public ModelAndView editCustom(@PathVariable int id) throws IllegalAccessException, InvocationTargetException {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    AuditDto auditDto = auditService.findWithDetailsById(id, true, true, false, false);
    CreateAssetAuditDto createAssetAuditDto = new CreateAssetAuditDto();
    BeanUtils.copyProperties(createAssetAuditDto, auditDto);
    List<SectionDto> savedSections = auditDto.getSections();
    List<Long> savedQuestionIds = new ArrayList<Long>();
    Map<Integer,Set<Long>> subAssetQuestionListMap=new HashMap<Integer, Set<Long>>();
    Map<Integer,Set<Long>> subAssetSectionListMap=new HashMap<Integer, Set<Long>>();

    for (SectionDto sectionDto : savedSections) {
      List<QuestionDto> sectionQuestion = sectionDto.getQuestions();
      Set<Long> questionList= subAssetQuestionListMap.get(sectionDto.getSubAssetId());
      if(questionList==null || questionList.isEmpty()){
          questionList=new HashSet<Long>();
      }
      for (QuestionDto questionDto : sectionQuestion) {
        if (questionDto.getParentId() != null) {
               questionList.add(questionDto.getParentId());
        }
      }
      subAssetQuestionListMap.put(sectionDto.getSubAssetId(), questionList);
      Set<Long> sectionList= subAssetSectionListMap.get(sectionDto.getSubAssetId());
      if(sectionList!=null&&!sectionList.isEmpty()){
          sectionList.add(sectionDto.getId());
          subAssetSectionListMap.put(sectionDto.getSubAssetId(), sectionList);
      }else{
          sectionList=new HashSet<Long>();
          sectionList.add(sectionDto.getId());
          subAssetSectionListMap.put(sectionDto.getSubAssetId(), sectionList);
      }
    }
    List<SubAssetDto> subAssetDtoList=new ArrayList<SubAssetDto>();
    for (Map.Entry<Integer,Set<Long>> entry : subAssetQuestionListMap.entrySet())
    {
        SubAssetDto dto=new SubAssetDto();
        dto.setSelectedParentQuestionIds(entry.getValue());
        dto.setSavedParentQuestionIds(entry.getValue());
        dto.setSelectedParentSectionIds(subAssetSectionListMap.get(entry.getKey()));
        dto.setSubAssetId(entry.getKey());
        subAssetDtoList.add(dto);
    }

    createAssetAuditDto.setSubAssetList(subAssetDtoList);
    List<Long> parentQuestions = new ArrayList<Long>();
    AuditDto parentDto = auditService.findWithDetailsById(auditDto.getParentId(), true, true, true);
    createAssetAuditDto.setParentAudit(parentDto);
    for (SectionDto sectionDto : parentDto.getSections()) {
      for (QuestionDto question : sectionDto.getQuestions()) {
        parentQuestions.add(question.getId());
      }
    }
    data.put("createAssetAuditDto", createAssetAuditDto);
    List<AssetDto> assetdtoList=assetService.findAll();
    data.put("assetdtoList", assetdtoList);
    parentQuestions.removeAll(savedQuestionIds);
    if (parentQuestions != null && !parentQuestions.isEmpty()) {
      data.put("showSave", true);
    } else {
      data.put("showSave", false);
    }
    data.put("type", "Edit");
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createassetaudit");
    return modelAndView;

  }



  
  
  
   * author: Ravi Sharma Date: 29/12/2016 This Get method is to create custom parent audit.
   
  @RequestMapping(value = "/createcustomparentaudit", method = RequestMethod.GET)
  public ModelAndView createCustomparentauditGet() {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    CreateAuditDto createAuditDto = new CreateAuditDto();
    data.put("createAuditDto", createAuditDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createcustomparentaudit");
    return modelAndView;
  }



  
   * Author: Ravi Sharma Date: 29/12/2016 This Post method is to create custom parent audit.
   
  @RequestMapping(value = "/createcustomparentaudit", method = RequestMethod.POST)
  public ModelAndView createCustomParentPost(@ModelAttribute CreateAuditDto createAuditDto, Errors errors,
      @RequestParam(required = false) String addMoreQuestion) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {

        auditService.saveAudit(createAuditDto);
        if ("true".equalsIgnoreCase(addMoreQuestion)) {
          createAuditDto.setSection(null);
          String createSuccessMsg = messageSource.getMessage("createaudit.success", null, Locale.ENGLISH);
          modelAndView.setViewName("redirect:/workspace/audit/addquestion/" + createAuditDto.getId() + "?createSuccessMsg=" + createSuccessMsg);
          return modelAndView;
        }

        modelAndView.addObject("success", messageSource.getMessage("createaudit.success", null, Locale.ENGLISH));

      } catch (DataAlreadyExistException e) {
        LOGGER.info("Custom parent audit with name : " + createAuditDto.getName() + " is already exist. message is:-", e);
        errors.rejectValue("name", "addaudit.alreadyexist");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView.addObject("error", messageSource.getMessage("createaudit.error", null, Locale.ENGLISH));
      }
    }
    data.put("createAuditDto", createAuditDto);
    data.put("addMoreQuestion", addMoreQuestion);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("createcustomparentaudit");
    return modelAndView;
  }



  
   * author: Ravi Sharma Date: 30/12/2016 This method is to search custom parent audit from Search
   * box.
   
  @RequestMapping(value = "/searchcustomparentaudit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<NameIdDto> searchCustomParentAudit( @RequestParam String auditType, @RequestParam String query) {
    return auditService.searchParentCustomAudit(AuditTypeEnum.PARENTCUSTOM, query);
  }




  
   * author: Ravi Sharma Date: 30/12/2016 This method is to view custom parent audit.
   
  @RequestMapping(value = "/viewcustomparentaudit/{id}", method = RequestMethod.GET)
  public ModelAndView viewCustomParentAudit(HttpSession session, @PathVariable Integer id, @RequestParam(required = false) String msg) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    AuditDto auditDto = auditService.findWithDetailsById(id, true, true, true, false);
    data.put("auditDto", auditDto);
//    String refererHeader = request.getHeader("Referer");

    if ("success".equals(session.getAttribute("deleteQuestion")) && refererHeader != null) {
      data.put("message", messageSource.getMessage("deleteQuestion.success", null, Locale.ENGLISH));
      session.removeAttribute("deleteQuestion");
    }
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("viewcustomparentaudit");
    return modelAndView;
  }

  

  
   * author: Ravi Sharma Date: 05/01/2017 This method is to convert audit type.
   
  @RequestMapping(value = "/convert/{id}", method = RequestMethod.GET)
  public ModelAndView convertAuditType(HttpSession session, @PathVariable int id) {
    ModelAndView modelAndView = new ModelAndView();
    String url = "../view/" + id;
    try {
      auditService.convert(id, null);
      session.setAttribute("convert", "success");
    } catch (DataAlreadyExistException e) {
      session.setAttribute("convertauditalreadyexist", "success");
      LOGGER.info("Error in conversion of audit : " + id, e);
    }
    modelAndView.setViewName("redirect:" + url);

    return modelAndView;
  }

//  @RequestMapping(value = "/getcustomparentsforconverison/{auditId}", method = RequestMethod.POST) 
//  public   ModelAndView convertToCustomAudit(HttpSession session, @PathVariable int auditId, @ModelAttribute CreateCustomAuditDto createAuditDto) {
//    ModelAndView modelAndView = new ModelAndView();
//    String url = "../view/" + auditId;
//    try {
//   auditService.convert(auditId, "custom" , createAuditDto.getParentId());
//      session.setAttribute("convert", "convert");
//    } catch (DataAlreadyExistException e) {
//      session.setAttribute("converterror", "converterror");
//      LOGGER.info("Error in conversion of audit : " + auditId, e);
//    } catch (Exception e) {
//      session.setAttribute("converterror", "converterror");
//      LOGGER.error("Error in conversion of audit : " + auditId, e);
//    }
//    modelAndView.setViewName("redirect:" + url);
//
//    return modelAndView;
//    }
//  
//  
//  @RequestMapping(value = "/getcustomparentsforconverison/{auditId}", method = RequestMethod.GET) 
//  public   ModelAndView getCustomParents(@PathVariable int auditId) {
//      ModelAndView modelAndView = new ModelAndView();
//      Map<String, Object> data = new HashMap<String, Object>();
//      List<NameIdDto> audits = auditService.findNameIdByAuditType(AuditTypeEnum.PARENTCUSTOM);
//      data.put("audits", audits);
//      CreateAuditDto createAuditDto =  new CreateAuditDto();
//      data.put("createAuditDto", createAuditDto);
//      modelAndView.addAllObjects(data);
//      modelAndView.setViewName("addparent");
//      return modelAndView;
//    }
  

  
  
  @RequestMapping(value = "/copyAudit/{id}", method = RequestMethod.GET)
  public ModelAndView copyAudit(@PathVariable int id) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    CreateAuditDto createAuditDto =  new CreateAuditDto();
    createAuditDto.setId(id);
    data.put("createAuditDto" , createAuditDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("copyAudit");
    return modelAndView;
  }
  
  @RequestMapping(value = "/copyAudit/{id}", method = RequestMethod.POST)
  public ModelAndView copyAuditPost(@PathVariable int id, @ModelAttribute CreateAuditDto createAuditDto , Errors errors) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    try {
      auditService.copyAudit(id , createAuditDto);
      modelAndView.addObject("success", messageSource.getMessage("copyaudit.success", null, Locale.ENGLISH));
    } catch (DataAlreadyExistException e) {
      LOGGER.info("Error in copyAuditPost of audit : " + id, e);
      errors.rejectValue("name", "addaudit.alreadyexist");
    }
    data.put("createAuditDto", createAuditDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("copyAudit");
    return modelAndView;
  }

  
  @RequestMapping(value = "/auditcomment/{auditId}/{shareHistoryId}", method = RequestMethod.GET)
  public ModelAndView auditcomment(@PathVariable int auditId, @PathVariable long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Object> data = new HashMap<String, Object>();
    List<AuditCommentDto> auditComments = auditResultService.getAuditComment(auditId, shareHistoryId);
    data.put("auditComments", auditComments);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("auditcomment");
    return modelAndView;
  }
  @RequestMapping(value = "/auditimage/{auditId}/{shareHistoryId}", method = RequestMethod.GET)
  public ModelAndView auditimage(@PathVariable int auditId, @PathVariable long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Object> data = new HashMap<String, Object>();
    List<AuditImageDto> auditImages = auditResultService.getAuditImages(auditId, shareHistoryId);
    data.put("auditImages", auditImages);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("auditimage");
    return modelAndView;
  }
  @RequestMapping(value = "/auditrespondent/{auditId}/{shareHistoryId}", method = RequestMethod.GET)
  public ModelAndView auditrespondent(@PathVariable int auditId, @PathVariable long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Object> data = new HashMap<String, Object>();
    AuditRespondentDto auditRespondentDto = auditResultService.findAuditRespondent(auditId, shareHistoryId);
    data.put("auditRespondentDto", auditRespondentDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("auditrespondent");
    return modelAndView;
  }

  @RequestMapping(value = "/optionrespondent/{optionId}/{shareHistoryId}", method = RequestMethod.GET)
  public ModelAndView optionrespondent(@PathVariable long optionId, @PathVariable long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Object> data = new HashMap<String, Object>();
    QuestionRespondentsDto questionRespondents = auditResultService.getOptionRespondent(optionId, shareHistoryId);
    data.put("questionRespondents", questionRespondents);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("questionrespondent");
    return modelAndView;
  }

  @RequestMapping(value = "/questionrespondent/{questionId}/{shareHistoryId}", method = RequestMethod.GET)
  public ModelAndView questionrespondent(@PathVariable long questionId, @PathVariable long shareHistoryId) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Object> data = new HashMap<String, Object>();
    QuestionRespondentsDto questionRespondents = auditResultService.getQuestionRespondent(questionId, shareHistoryId);
    data.put("questionRespondents", questionRespondents);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("questionrespondent");
    return modelAndView;
  }

  @RequestMapping(value = "/summary", method = RequestMethod.POST)
  @ResponseBody
  public String saveSectionSummary(@ModelAttribute SummaryDto summaryDto) {
      switch(summaryDto.getsType()){
      case "sectionSummary":{
          sectionService.updateSection(summaryDto.getId(),summaryDto.getSummary());
          break;
      }
      case "execSummary":{
          auditService.updateSummary(summaryDto.getId(),summaryDto.getSummary(),false,summaryDto.getShareHistoryId());
          break;  
      }
      case "auditAction":{
          auditService.updateSummary(summaryDto.getId(),summaryDto.getSummary(),true,0);
          break;
      }
      default:break;
      }
      return "success";
  }
  
  @RequestMapping(value = "/scr", method = RequestMethod.POST)
  @ResponseBody
  public String saveScr(@ModelAttribute SummaryDto summaryDto) {
      try{
      auditService.updateScr(summaryDto.getSummary(),summaryDto.getShareHistoryId());
      return "success";
      }catch(Exception e){
          LOGGER.error("Error: ",e);
          return "error";
      }
  }
  
  @RequestMapping(value = "/savename", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> saveNames(@ModelAttribute UpdateNameDto updateNameDto) {
      Map<String, Object> data = new HashMap<String, Object>();
      AuditDto auditDto = auditService.findWithDetailsById(id, true, true, true, false);
      data.put("auditDto", auditDto);
      switch(updateNameDto.getsType()){
      case "sectionName":{
          try{
          sectionService.updateSectionInfo(updateNameDto.getId(),updateNameDto.getName().trim(),updateNameDto.getAuditType());         
          data.put("success", true);
          }catch(Exception e){
              data.put("success", false);
              LOGGER.error("Error while adding the audit :",e);
              data.put("message", messageSource.getMessage("updatesection.alreadyexist", null, Locale.ENGLISH)); 
          }
          break;
      }
      case "auditName":{
          try{
          auditService.updateAuditInfo(updateNameDto.getId(),updateNameDto.getName().trim(),updateNameDto.getAuditType());
          data.put("success", true);
      }catch(DataAlreadyExistException e){
          data.put("success", false);
          LOGGER.error("Error while adding the audit :",e);
          data.put("message", messageSource.getMessage("addaudit.alreadyexist", null, Locale.ENGLISH));
          break;  
      }
      }
      default:break;
      }
      return data;
  }
  
 
  @RequestMapping(value = "/getAllUserList/{auditId}", method = RequestMethod.GET)
  @ResponseBody
  public List<ShareUserDto> getAllUserList(@PathVariable int auditId){
    return auditService.getUsersForSharing(auditId);
  }
  @RequestMapping(value = "/getAllUserList", method = RequestMethod.GET)
  @ResponseBody
  public List<ShareUserDto> getAllUserList(@RequestParam boolean isUSer){
    return auditService.getUsers(isUSer);
  }
  
  @RequestMapping(value = "/updateSection/{sectionId}/{questionId}/{sequence}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> updateSection( HttpSession session, @PathVariable long sectionId,@PathVariable long questionId,@PathVariable int sequence) {
    Map<String, Object> data = new HashMap<String, Object>();
    questionService.updateSectionId(questionId,sectionId,sequence);
    session.setAttribute("sectionUpdate", "success");
    data.put("success", true);
    return data;
  }
  
  @ResponseBody
  @RequestMapping(value = "/preparesharedaudit/{id}/{shareHistoryId}", method = RequestMethod.GET)
  public Map<String,String>  downloadDocument(@PathVariable int id, @PathVariable Long shareHistoryId) {
    Map<String, String> data = new HashMap<String, String>();
    AuditResultsDto auditResultDto = auditResultService.findResultsDetailById(id, shareHistoryId);
    File file = new File(ApplicationConstants.DOC_PATH);
    String fileName="auditId_"+auditResultDto.getId()+"_"+GeneralUtil.getUserId()+"_"+System.currentTimeMillis()+".doc";
    file.mkdirs();
    file = new File(ApplicationConstants.DOC_PATH,fileName);
    if(file.exists()){
        file.delete();
    }
    try {
     file.createNewFile();
 } catch (IOException e) {
     LOGGER.error("Error while creating new file",e);
 }
   
   data.put("finalFileName", auditResultDto.getName().replaceAll("[^a-zA-Z0-9.-]", "_"));
   data.put("fileName", fileName);
   data.put("isSuccess",String.valueOf(auditService.createDocForViewUser(auditResultDto,file)));
   return data;
}
  
  
  @ResponseBody
  @RequestMapping(value = "/downloadsharedaudit", method = RequestMethod.GET)
  public void downloadsharedaudit(HttpServletResponse response,@RequestParam String fileName,@RequestParam String finalFileName) throws IOException {
      File file = new File(ApplicationConstants.DOC_PATH,fileName);
      byte[] bFile = new byte[(int) file.length()];
      if(file.exists()){
          FileInputStream fileInputStream = null;
          fileInputStream = new FileInputStream(file);
          fileInputStream.read(bFile);
          fileInputStream.close();
      }
      response.setContentType(ExtensionMime.TYPE.get(ExtensionMime.DOCX));
      response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "_" + GeneralUtil.getDate() + ".docx\"");
      response.getOutputStream().write(bFile);
      response.flushBuffer();
  }
  
  @ResponseBody
  @RequestMapping(value = "/deleteSubasset/{auditId}/{subAssetId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String,Object> deleteSubasset(@PathVariable int auditId,@PathVariable int subAssetId){
      Map<String, Object> data = new HashMap<String, Object>();
      sectionService.deleteSections(auditId,subAssetId);
      data.put("success", true);
      return data;
      
  }
  
  @ResponseBody
  @RequestMapping(value = "/savedownloadoptions/{shareHistoryId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String,Object> saveDownloadOptions(@ModelAttribute DownloadOptions downloadOptions,@PathVariable long shareHistoryId){
      Map<String, Object> data = new HashMap<String, Object>();
      try{
      downloadOptionService.saveDownloadOptions(downloadOptions, shareHistoryId);
      data.put("success", true);
      }catch(Exception e){
          data.put("success", false);
          LOGGER.error("Error while saving download options :",e);   
      }
      return data;
      
  }
  @ResponseBody
  @RequestMapping(value = "/saveFindings/{shareHistoryId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String,Object> saveFindings(@ModelAttribute FindingsListDto dto,@PathVariable long shareHistoryId){
      Map<String, Object> data = new HashMap<String, Object>();
      try{
          if(dto!=null&&dto.getData()!=null&&!dto.getData().isEmpty()){
          findingsService.savefindings(dto.getData(), shareHistoryId);
          }
      data.put("success", true);
      }catch(Exception e){
          data.put("success", false);
          LOGGER.error("Error while saving findings :",e);   
      }
      return data;
      
  }
 @RequestMapping(value = "/archive/{auditId}", method = RequestMethod.GET)
  public ModelAndView archiveAudit(HttpSession session,@PathVariable int auditId){
    ModelAndView modelAndView = new ModelAndView();
    try {
      if (auditService.archiveAudit(auditId)) {
        modelAndView.setViewName("redirect:../archive");
        session.setAttribute("archive" ,true);
      }else{
          modelAndView.setViewName("redirect:../manage");
          session.setAttribute("error" ,"stillShared");
      }
    } catch (Exception e) {
        modelAndView.setViewName("redirect:../manage");
        session.setAttribute("error" ,"errorArchive");
        LOGGER.error("Error while archiving :",e);   
    }
    return modelAndView;
    }
 
	 @RequestMapping(value = "/unarchive/{auditId}/{useOldSharing}", method = RequestMethod.GET)
	 public ModelAndView unArchiveAudit(HttpSession session,@PathVariable int auditId, @PathVariable boolean useOldSharing){
	   ModelAndView modelAndView = new ModelAndView();
	   try {
	     if (auditService.unArchiveAudit(auditId,useOldSharing)) {
	       modelAndView.setViewName("redirect:../../archive");
	       session.setAttribute("unarchive" ,true);
	     }else{
	         modelAndView.setViewName("redirect:../manage");
	         session.setAttribute("error" ,"errorUnArchive");
	     }
	   } catch (Exception e) {
	       modelAndView.setViewName("redirect:../manage");
	       session.setAttribute("error" ,"errorUnArchive");
	       LOGGER.error("Error while unarchiving :",e);   
	   }
	   return modelAndView;
	   }
	 
  @RequestMapping(value = "/archive", method = RequestMethod.GET)
  public ModelAndView archive(HttpSession session, @RequestParam(required = false) Integer pageNum,
          @RequestParam(required = false) String auditType, AdSearchDto adSearchDto){
      if (pageNum == null || pageNum <= 0) {
          pageNum = 1;
        }

        if (StringUtils.isEmpty(auditType)) {
          auditType = "allAudit";
        }
      ModelAndView modelAndView = new ModelAndView();
      Map<String, Object> data = new HashMap<>();
      if(session.getAttribute("archive")!=null&&(boolean) session.getAttribute("archive")){
          data.put("message", messageSource.getMessage("audit.archivesuccess", null, Locale.ENGLISH));
          session.removeAttribute("archive");
      }
      if(session.getAttribute("unarchive")!=null&&(boolean) session.getAttribute("unarchive")){
          data.put("message", messageSource.getMessage("audit.unarchivesuccess", null, Locale.ENGLISH));
          session.removeAttribute("unarchive");
      }
          List<AuditStatusEnum> statusList = new ArrayList<>();
          statusList.add(AuditStatusEnum.NOT_PUBLISH);
          statusList.add(AuditStatusEnum.PUBLISH);
          List<AuditDto> audits = auditService.findAllByStatusIn(statusList, false, false, false, pageNum, MAXRESULT, auditType,true);
          data.put("audits", audits);
          data.put(AUDIT_ARCHIVE_CLASS, ACTIVE);
          data.put("archive",true);
          data.put("pageNum", pageNum);
          data.put("auditType", auditType);
          
          data.put("totalPages", auditService.getCount(MAXRESULT, statusList, auditType,true));
          modelAndView.addAllObjects(data);
          modelAndView.setViewName("manageaudit");
    return modelAndView;
      
  }
  
  
	@RequestMapping(value = "/advmanage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> AdvManage(@Valid @ModelAttribute AdSearchDto adSearchDto) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (adSearchDto.getPageNum() == null || adSearchDto.getPageNum() <= 0) {
			adSearchDto.setPageNum(1);
		}
		if (StringUtils.isEmpty(adSearchDto.getAuditType())) {
			adSearchDto.setAuditType("allAudit");
		}
		try {
		    AddvAuditReportListDto auditsByAdvanceSearch = auditService.findAuditsByAdvanceSearch(MAXRESULT, adSearchDto);
		    data.put("audits", auditsByAdvanceSearch.getAuditDtoList());
		    data.put("pageNum", adSearchDto.getPageNum());
		    data.put("adSearchDto", adSearchDto);
		    data.put("archive",adSearchDto.isArchive());
		    data.put("totalPages", auditsByAdvanceSearch.getTotalPages());
		    
			data.put("success", true);
		} catch (NullPointerException | IllegalArgumentException
				| InvalidDataAccessApiUsageException e) {
			LOGGER.error("Error : ", e);
			data.put("success", false);
		} catch (Exception e) {
			data.put("success", false);
			LOGGER.error("Error : ", e);
		}
		return data;
	}
	
	@RequestMapping(value = "/search/{auditId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  List<ShareUserDto> searchUser(@PathVariable int auditId, @RequestParam String query) {
		return auditService.getUsersForNotification(auditId, query);
	}
	@RequestMapping(value = "/notification/{auditId}", method = RequestMethod.GET)
	public ModelAndView auditNotification(@PathVariable int auditId) {
	    ModelAndView modelAndView = new ModelAndView();
	    Map<String, Object> data = new HashMap<>();
	    AuditNotificationDto auditNotificationDto = auditService.emailNotification(auditId);
	    data.put("auditId", auditId);
	    data.put("auditNotificationDto", auditNotificationDto);
	    modelAndView.setViewName("auditNotification");
	    modelAndView.addAllObjects(data);
	    return modelAndView;
	}
	
	@RequestMapping(value = "/notification/save/{auditId}", method = RequestMethod.POST)
	  public ModelAndView auditNotification(@PathVariable int auditId, @Valid @ModelAttribute AuditNotificationDto auditNotificationDto) {
	    ModelAndView modelAndView = new ModelAndView();
	    Map<String, Object> data = new HashMap<>();

	    auditService.addAuditEmailNotification(auditId, auditNotificationDto);
	    data.put("success", "auditnotification.success");
	    data.put("auditNotificationDto", auditNotificationDto);
	    data.put("auditId", auditId);
	    modelAndView.setViewName("auditNotification");
	    modelAndView.addAllObjects(data);
	    return modelAndView;
	  }
}
*/
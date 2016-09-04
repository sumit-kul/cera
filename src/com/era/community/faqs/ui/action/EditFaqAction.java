package com.era.community.faqs.ui.action;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.AppConstants;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.faqs.dao.Faq;
import com.era.community.faqs.dao.FaqFinder;
import com.era.community.faqs.ui.dto.FaqDto;
import com.era.community.faqs.ui.validator.FaqValidator;

/**
 *  @spring.bean name="/faq/faqEdit.do"
 */
public class EditFaqAction extends AbstractFormAction
{
	protected FaqFinder faqFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "faq/editFaq";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;
		Faq faq = faqFinder.getFaqForId(cmd.getId());
		cmd.copyPropertiesFrom(faq);
		cmd.setMode(AppConstants.MODE_UPDATE);
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;

		Faq faq = faqFinder.getFaqForId(cmd.getId());
		faq.setSubject(cmd.getSubject());
		faq.setBody(cmd.getBody());

		faq.update();

		MultipartFile file = cmd.getUpload();
		if (file != null && !file.isEmpty()) {
			faq.storeFile(file);
			faq.setFileName(file.getOriginalFilename());
			faq.update();
		}
		return new ModelAndView("redirect:/faq/showAllFaqs.do");
	}

	public static class Command extends FaqDto implements CommandBean
	{
	}

	public class Validator extends FaqValidator
	{

	}

	public final void setFaqFinder(FaqFinder faqFinder)
	{
		this.faqFinder = faqFinder;
	}
}
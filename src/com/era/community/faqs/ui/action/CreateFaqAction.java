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
 *  @spring.bean name="/faq/faqCreate.do"
 */
public class CreateFaqAction extends AbstractFormAction
{
	protected FaqFinder faqFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "faq/createFaq";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;
		cmd.setMode(AppConstants.MODE_CREATE);
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;

		Faq faq = faqFinder.newFaq();

		faq.setSubject(cmd.getSubject());
		faq.setBody(cmd.getBody());
		faq.setSequence(faq.getId());

		faq.update();

		if (cmd.getUpload() != null && !cmd.getUpload().isEmpty()) {

			MultipartFile file = cmd.getUpload();
			faq.storeFile(file);
			faq.setFileName(file.getOriginalFilename());

		}
		faq.update();

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
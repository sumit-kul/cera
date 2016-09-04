package com.era.community.base;

import java.awt.RenderingHints;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;

import com.era.community.media.ui.dto.ThumbnailDto;
import com.era.community.upload.dao.Image;
import com.sun.media.jai.codec.SeekableStream;

public class ThumbnailManipulation {

	public static ThumbnailDto resizeImageAsJPG(InputStream imageInputStream, int pMaxWidth, CommunityEraContext context) throws Exception {
		// read in the original image from an input stream
		SeekableStream seekableImageStream = SeekableStream.wrapInputStream(imageInputStream, true);
		RenderedOp originalImage = JAI.create("stream", seekableImageStream);
		((OpImage) originalImage.getRendering()).setTileCache(null);
		
		// now resize the image
		double scale = (double) pMaxWidth / originalImage.getWidth();
		ParameterBlock paramBlock = new ParameterBlock();
		paramBlock.addSource(originalImage); // The source image
		paramBlock.add(scale); // The xScale
		paramBlock.add(scale); // The yScale
		paramBlock.add(0.0); // The x translation
		paramBlock.add(0.0); // The y translation

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		RenderedOp resizedImage = JAI.create("SubsampleAverage", paramBlock, qualityHints);

		// lastly, write the newly-resized image to an output stream, in a specific encoding
		ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
		JAI.create("encode", resizedImage, encoderOutputStream, "PNG", null);
		// Export to Byte Array
		int newsize = encoderOutputStream.size();
		byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
		ThumbnailDto dto = new ThumbnailDto();
		dto.setThumbnail(resizedImageByteArray);
		dto.setEncoding("image/jpeg");
		dto.setNewSize(newsize);
		dto.setNewWidth(resizedImage.getWidth());
		dto.setNewHeight(resizedImage.getHeight());
		return dto;
	}

	private static void setItem (Image image, int itemId, String type) {
		if ("BlogEntry".equalsIgnoreCase(type)) {
			image.setBlogEntryId(itemId);
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			image.setWikiEntryId(itemId);
		}  else if ("WikiEntrySection".equalsIgnoreCase(type)) {
			image.setWikiEntrySectionId(itemId);
		} else if ("ForumItem".equalsIgnoreCase(type)) {
			image.setForumItemId(itemId);
		} else if ("BlogEntryComment".equalsIgnoreCase(type)) {
			image.setBlogEntryCommentId(itemId);
		} else if ("WikiEntryComment".equalsIgnoreCase(type)) {
			image.setWikiEntryCommentId(itemId);
		} else if ("CommunityDescription".equalsIgnoreCase(type)) {
			image.setCommunityDescriptionId(itemId);
		} else if ("AboutMe".equalsIgnoreCase(type)) {
			image.setAboutMeId(itemId);
		} else if ("Message".equalsIgnoreCase(type)) {
			image.setMessageId(itemId);
		} 
	}
}
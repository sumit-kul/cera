package com.era.community.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.era.community.upload.dao.Image;
import com.era.community.upload.dao.ImageFinder;

public class ImageManipulation {
	private static ImageFinder imageFinder;
	private static final String JPEG_CONTENT_TYPE = "image/jpeg";

	public static synchronized String manageImages(CommunityEraContext context, String data, String header, int userId, int itemId, String type) throws Exception {
		int count = 1;
		boolean hasThumbnail = false;
		org.jsoup.nodes.Document doc = Jsoup.parse(data);
		header = header == null ? "" : header;
		String name = header.length() < 10 ? header : header.substring(0, 9);
		Elements imgs = doc.select("img");
		//imageFinder.deleteImagesForItemType(itemId, type);
		imageFinder.markImagesForDeletion(itemId, type);
		for(Element img : imgs) {
			name = name + RandomString.nextShortString();
			if(img.hasAttr("src")) {
				String imgSrc = img.attr("src");
				String truncStr = "";

				if (imgSrc.startsWith("data:image/png;base64,") || imgSrc.startsWith("data:image/jpeg;base64,")) {
					if (imgSrc.startsWith("data:image/png;base64,")) {
						truncStr = imgSrc.replace("data:image/png;base64,", "");
					}
					if (imgSrc.startsWith("data:image/jpeg;base64,")) {
						truncStr = imgSrc.replace("data:image/jpeg;base64,", "");
					}

					byte[] imageByteArray = Base64.decodeBase64(truncStr);
					InputStream is = new ByteArrayInputStream(imageByteArray);

					Image image = imageFinder.newImage();
					image.setFileName(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
					image.setFileLength(imageByteArray.length);
					image.setFileContentType("image/jpeg");
					image.setPosterId(userId);
					image.setTitle(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
					setItem(image, itemId, type);
					image.update();

					String url = context.getContextUrl()+"/upload/imageDisplay.img?id="+image.getId();
					img.attr("src", url);

					if (img.hasAttr("width")) {
						if (img.hasAttr("style")) {
							String st = img.attr("style");
							st.concat(";max-width: 726px;");
							img.attr("style", st);
						} else {
							img.attr("style", "max-width: 726px;");
						}
						if (img.hasAttr("height")) {
							img.removeAttr("height");
						}
					}
					
					try {
						BufferedImage i = ImageIO.read(is);
						int width = i.getWidth();
						int height = i.getHeight();
						resize(image, i, width < 730 ? width : 730, height < 730 ? height : 730);
						if (!hasThumbnail) {
							hasThumbnail = resizeThumbnail(image, i, width, height);
						}
						i.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}

					count++;
				} else if (imgSrc.startsWith("http://") || imgSrc.startsWith("https://")) {
					try {
						if (imgSrc.startsWith("http://jhapak.com")) {
							imgSrc = imgSrc.replace("http", "https");
						}
						URL url = new URL(imgSrc);
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect(); 

						Image image = imageFinder.newImage();
						image.setFileName(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
						image.setFileContentType("image/jpeg");
						image.setPosterId(userId);
						image.setTitle(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
						setItem(image, itemId, type);
						image.update();

						String nurl = context.getContextUrl()+"/upload/imageDisplay.img?id="+image.getId();
						img.attr("src", nurl);

						if (img.hasAttr("width")) {
							if (img.hasAttr("style")) {
								String st = img.attr("style");
								st.concat(";max-width: 726px;");
								img.attr("style", st);
							} else {
								img.attr("style", "max-width: 726px;");
							}
							if (img.hasAttr("height")) {
								img.removeAttr("height");
							}
						}

						try {
							BufferedImage i = ImageIO.read(conn.getInputStream());
							int width = i.getWidth();
							int height = i.getHeight();
							resize(image, i, width < 730 ? width : 730, height < 730 ? height : 730);
							if (!hasThumbnail) {
								hasThumbnail = resizeThumbnail(image, i, width, height);
							}
							i.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}

						count++;
					}
					catch (IOException e)
					{
						// do nothing ...keep original url with src attribute...
					}
				}
			}
		}

		Elements ifrms = doc.select("iframe");
		if (ifrms.size() > 0) {
			Element frame = ifrms.get(0);
			name = name + RandomString.nextShortString();
			if(frame != null && frame.hasAttr("src")) {
				String frmSrc = frame.attr("src");
				String thumbSrc = "";
				if (frmSrc.contains("www.youtube.com")) {
					String[] output = frmSrc.split("embed/");
					if (output.length == 2) {
						thumbSrc = "http://img.youtube.com/vi/"+output[1]+"/0.jpg";
					}
					if (!"".equals(thumbSrc)) {
						try {
							URL url = new URL(thumbSrc);
							URLConnection conn = url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.connect(); 

							Image image = imageFinder.newImage();
							image.setFileName(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
							image.setFileContentType("image/jpeg");
							image.setPosterId(userId);
							image.setTitle(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
							image.setThumbnail(1);
							setItem(image, itemId, type);
							image.update();

							try {
								BufferedImage i = ImageIO.read(conn.getInputStream());
								if (!hasThumbnail) {
									hasThumbnail = resizeThumbnail(image, i, i.getWidth(), i.getHeight());
								}
								i.flush();
							} catch (Exception e) {
								e.printStackTrace();
							}
							count++;
						}
						catch (IOException e)
						{
						}
					}
				}
			}
		}

		if (!hasThumbnail) {
			Elements fbvideos = doc.getElementsByAttributeValue("class", "fb-video");
			if (fbvideos.size() > 0) {
				Element fbV = fbvideos.get(0);
				name = name + RandomString.nextShortString();
				if(fbV != null && fbV.hasAttr("data-href")) {
					String frmSrc = fbV.attr("data-href");
					String thumbSrc = "";
					if (frmSrc.contains("www.facebook.com")) {
						String[] output = frmSrc.split("/videos/");
						if (output.length == 2) {
							String fbId = output[1];
							if (!fbId.endsWith("/")) {
								fbId.concat("/");
							}
							thumbSrc = "https://graph.facebook.com/"+fbId+"picture";
						}
						if (!"".equals(thumbSrc)) {
							try {
								URL url = new URL(thumbSrc);
								URLConnection conn = url.openConnection();
								conn.setConnectTimeout(5000);
								conn.setReadTimeout(5000);
								conn.connect(); 

								Image image = imageFinder.newImage();
								image.setFileName(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
								image.setFileContentType("image/jpeg");
								image.setPosterId(userId);
								image.setTitle(name.length() > 200 ? name.subSequence(0, 199).toString() : name);
								image.setThumbnail(1);
								setItem(image, itemId, type);
								image.update();

								try {
									BufferedImage i = ImageIO.read(conn.getInputStream());
									//resize(image, i, 660, 660);
									if (!hasThumbnail) {
										
										hasThumbnail = resizeThumbnail(image, i, i.getWidth(), i.getHeight());
									}
									i.flush();
								} catch (Exception e) {
									e.printStackTrace();
								}
								count++;
							}
							catch (IOException e)
							{
							}
						}
					}
				}
			}
		}
		imageFinder.deleteImagesForItemType(itemId, type);
		return doc.html();
	}

	private static void resize(Image image, BufferedImage input, int width, int height) throws Exception {
		try {
			BufferedImage out = Scalr.resize(input, width, height);
			ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
			ImageIO.write(out, "JPEG", encoderOutputStream);
			byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
			image.storeBlob(new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), JPEG_CONTENT_TYPE, out.getWidth(), out.getHeight());
			out.flush(); encoderOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean resizeThumbnail(Image image, BufferedImage input, int widthin, int heightin) throws Exception {
		boolean hasThumbnail = false;
		int width = widthin < 260 ? widthin : 260;
		int height = heightin < 260 ? heightin : 260;
		try {
			if (image.getBlogEntryId() > 0) {
				width = widthin < 400 ? widthin : 400;
				height = heightin < 400 ? heightin : 400;;
			}/* else if (image.getWikiEntryId() > 0 || image.getWikiEntrySectionId() > 0){
				width = 260;
				height = 260;
			}*/

			if (width > 0 && height > 0) {
				BufferedImage out = Scalr.resize(input, width, height);
				ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
				ImageIO.write(out, "JPEG", encoderOutputStream);
				byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
				image.storeBlobThumbnail(new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), JPEG_CONTENT_TYPE, out.getWidth(), out.getHeight());
				out.flush(); encoderOutputStream.flush();

				hasThumbnail = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasThumbnail;

	}

	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
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
		} else if ("Assignment".equalsIgnoreCase(type)) {
			image.setAssignmentId(itemId);
		}
	}
}
/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support.community.framework.taglib;

import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

/**
 * Utility class for writing HTML content to {@link Writer} instance.
 * Intended to support output from JSP tag libraries.
 * 
 * @author Rob Harrop
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class TagWriter {


	/**
	 * {@link SafeWriter} to write to.
	 */
	private SafeWriter writer;


	/**
	 * Stores {@link TagStateEntry tag state}. Stack model naturally supports tag nesting.
	 */
	private Stack tagState = new Stack();


	/**
	 * Creates a <code>TagWriter</code> that writes to the supplied {@link Writer}.
	 */
	public TagWriter(Writer writer) {
		this.writer = new SafeWriter(writer);
	}
    
    protected SafeWriter getWriter()
    {
      return writer;
    }
    

	/**
	 * Starts a new tag with the supplied name. Leaves the tag open so
	 * that attributes, inner text or nested tags can be written into it.
	 * @see #endTag()
	 */
	public void startTag(String tagName) throws JspException {
		if (inTag()) {
			closeTagAndMarkAsBlock();
		}

		push(tagName);

		this.writer.append("<").append(tagName);
	}

	/**
	 * Writes an HTML attribute with the specified name and value. Be sure to
	 * write all attributes <strong>before</strong> writing any inner text or
	 * nested tags.
	 * @throws IllegalStateException if the opening tag is closed.
	 */
	public void writeAttribute(String attributeName, String attributeValue) throws JspException {
		if (currentState().isBlockTag()) {
			throw new IllegalStateException("Cannot write attributes after opening tag is closed.");
		}
		this.writer.append(" ").append(attributeName).append("=\"").append(attributeValue).append("\"");
	}

	/**
	 * Writes an HTML attribute if the supplied value is not <code>null</code> or
	 * zero length.
	 * @see #writeAttribute(String, String)
	 */
	public void writeOptionalAttributeValue(String attributeName, String attributeValue) throws JspException {
		if (StringUtils.hasText(attributeValue)) {
			writeAttribute(attributeName, attributeValue);
		}
	}

	/**
	 * Closes the current opening tag (if necessary) and appends the supplied value
	 * as inner text.
	 * @throws IllegalStateException if no tag is open.
	 */
	public void appendValue(String value) throws JspException {
		if (!inTag()) {
			throw new IllegalStateException("Cannot write tag value. No open tag available.");
		}
		closeTagAndMarkAsBlock();
		this.writer.append(value);
	}


	/**
	 * Indicates that the currently open tag should be closed and marked as a block level element.
	 * Useful when you plan to write additional content in the body outside the context of the current
	 * <code>TagWriter</code>.
	 */
	public void forceBlock() throws JspException {
		if (currentState().isBlockTag()) {
			return; // just ignore since we are already in the block
		}
		closeTagAndMarkAsBlock();
	}

	/**
	 * Closes the current tag. Correctly writes an empty tag if no inner text
	 * or nested tags have been written.
	 */
	public void endTag() throws JspException {
		if (!inTag()) {
			throw new IllegalStateException("Cannot write end of tag. No open tag available.");
		}

		if (currentState().isBlockTag()) {
			// writing the end of the block - the opening tag was closed earlier.
			this.writer.append("</").append(currentState().getTagName()).append(">");
		}
		else {
			this.writer.append("/>");
		}

		// remove the current tag from the state stack
		this.tagState.pop();
	}

	/**
	 * Adds the supplied tag name to the {@link #tagState tag state}.
	 */
    private void push(String tagName) {
		// create an entry for this tag on the stack
		TagStateEntry entry = new TagStateEntry();
		entry.setTagName(tagName);
		this.tagState.push(entry);
	}

	/**
	 * Closes the current opening tag and marks it as a block tag.
	 */
	private void closeTagAndMarkAsBlock() throws JspException {
		if (!currentState().isBlockTag()) {
			currentState().setBlockTag(true);
			this.writer.append(">");
		}
	}

	private boolean inTag() {
		return (this.tagState.size() > 0);
	}

	private TagStateEntry currentState() {
		return (TagStateEntry) this.tagState.peek();
	}


	/**
	 * Simple data object holding state about a tag and its
	 * rendered behaviour.
	 */
	private static class TagStateEntry {

		private String tagName;

		private boolean blockTag;

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public String getTagName() {
			return tagName;
		}

		public void setBlockTag(boolean blockTag) {
			this.blockTag = blockTag;
		}

		public boolean isBlockTag() {
			return blockTag;
		}
	}


	/**
	 * Simple wrapper around a {@link Writer} that wraps all exceptions
	 * in {@link JspException JspExceptions}.
	 */
	public static class SafeWriter {

		private final Writer writer;

		public SafeWriter(Writer writer) {
			this.writer = writer;
		}

        public SafeWriter append(String value) throws JspException {
            try {
                this.writer.write(String.valueOf(value));
                return this;
            }
            catch (IOException ex) {
                throw new JspException("Unable to write to JspWriter", ex);
            }
        }
        public SafeWriter print(String value) throws JspException {
            try {
                this.writer.write(String.valueOf(value));
                return this;
            }
            catch (IOException ex) {
                throw new JspException("Unable to write to JspWriter", ex);
            }
        }
        public SafeWriter println(String value) throws JspException {
            try {
              this.writer.write(String.valueOf(value));
              this.writer.write("\n");
                return this;
            }
            catch (IOException ex) {
                throw new JspException("Unable to write to JspWriter", ex);
            }
        }
	}

}

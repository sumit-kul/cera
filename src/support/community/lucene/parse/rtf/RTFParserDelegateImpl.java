/**
 * Copyright 2005 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support.community.lucene.parse.rtf;

// RTF Parser imports
import com.etranslate.tm.processing.rtf.*;

// JDK imports
import java.util.Arrays;
import java.util.List;


/**
 * A parser delegate for handling rtf events.
 * @author Andy Hedges
 */
public class RTFParserDelegateImpl implements RTFParserDelegate 
{

	String[] META_NAMES_TEXT = {
			"title",
			"subject",
			"Author",
			"manager",
			"company",
			"operator",
			"category",
			"Keywords",
			"Comments",
			"doccomm",
			"hlinkbase"
	};

	List metaNamesText = Arrays.asList(META_NAMES_TEXT);
	boolean isMetaTextValue = false;
	boolean isMetaDateValue = false;
	String content = "";
	boolean justOpenedGroup = false;
	boolean ignoreMode = false;

	public void text(String text, String style, int context) 
	{
		justOpenedGroup = false;
		if (isMetaTextValue && context == IN_INFO) {
			isMetaTextValue = false;
		} else if (context == IN_DOCUMENT && !ignoreMode) {
			content += text;
		}
	}

	public void controlSymbol(String controlSymbol, int context) 
	{
		if("\\*".equals(controlSymbol) && justOpenedGroup){
			ignoreMode = true;
		}
		justOpenedGroup = false;
	}

	public void controlWord(String controlWord, int value, int context) {
		justOpenedGroup = false;
		controlWord = controlWord.substring(1);
		switch (context) {
		case IN_INFO:
			if (metaNamesText.contains(controlWord)) {
				isMetaTextValue = true;
			} 
			break;
		case IN_DOCUMENT:
			break;
		}
	}

	public void openGroup(int depth) 
	{
		justOpenedGroup = true;
	}

	public void closeGroup(int depth) 
	{
		justOpenedGroup = false;
		ignoreMode = false;
	}

	public void styleList(List styles) {
	}

	public void startDocument() {
	}

	public void endDocument() {
	}

	public String getText() {
		return content;
	}

}

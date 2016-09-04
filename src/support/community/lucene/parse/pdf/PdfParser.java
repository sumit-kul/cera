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

package support.community.lucene.parse.pdf;

import support.community.lucene.index.*;

import org.pdfbox.encryption.*;
import org.pdfbox.pdfparser.*;
import org.pdfbox.pdmodel.*;
import org.pdfbox.util.*;

import java.io.*;

/*********************************************
 * parser for mime type application/pdf.
 * It is based on org.pdfbox.*. We have to see how well it does the job.
 * 
 * @author John Xing
 *
 * Note on 20040614 by Xing:
 * Some codes are stacked here for convenience (see inline comments).
 * They may be moved to more appropriate places when new codebase
 * stabilizes, especially after code for indexing is written.
 *
 *********************************************/

public class PdfParser implements TextExtractor  
{
/*
    public static final Logger LOG = LogFormatter.getLogger("org.apache.nutch.parse.pdf");

  public PdfParser () {
    org.apache.log4j.Logger rootLogger =
      org.apache.log4j.Logger.getRootLogger();

    rootLogger.setLevel(org.apache.log4j.Level.INFO);

    org.apache.log4j.Appender appender = new org.apache.log4j.WriterAppender(
      new org.apache.log4j.SimpleLayout(),
      org.apache.nutch.util.LogFormatter.getLogStream(
        this.LOG, java.util.logging.Level.INFO));

    rootLogger.addAppender(appender);
  }
*/
  public String extractText(InputStream is) throws Exception 
  {
  
    // in memory representation of pdf file
    PDDocument pdf = null;

    String text = null;
    String title = null;

    try {

      PDFParser parser = new PDFParser(is);
      parser.parse();

      pdf = parser.getPDDocument();

      if (pdf.isEncrypted()) {
        DocumentEncryption decryptor = new DocumentEncryption(pdf);
        //Just try using the default password and move on
        decryptor.decryptDocument("");
      }

      // collect text
      PDFTextStripper stripper = new PDFTextStripper();
      text = stripper.getText(pdf);

      // collect title
      PDDocumentInformation info = pdf.getDocumentInformation();
      title = info.getTitle();
      // more useful info, currently not used.
      // pdf.getPageCount();
      // info.getAuthor()
      // info.getSubject()
      // info.getKeywords()
      // info.getCreator()
      // info.getProducer()
      // info.getTrapped()
      // info.getCreationDate()
      // info.getModificationDate()

    } 
    finally {
      try {
        if (pdf != null)
          pdf.close();
        } catch (IOException e) {
          // nothing to do
        }
    }

    if (text == null)
      text = "";

    if (title == null)
      title = "";

    return text;

  }

}

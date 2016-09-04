package com.era.community.config.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.mail.SimpleMailMessage;


/*
 * For making email messages configurable
 * Spring reads the XML file mail-messages.xml
 */
public class MailMessageConfig
{
    /* Injected references */
    protected MailMessageDefinitionFinder mailMessageDefinitionFinder;
    
    // Map of key values pairs
    Map<String, MailMessageDefinition> messages;

    public final void setMessages(List<MailMessageDefinition> list) 
    {
        
        // TreeMap keeps the elements in order at all times
        messages = new TreeMap<String, MailMessageDefinition>();
        
        for (MailMessageDefinition m : list) {
            if (messages.containsKey(m.getName()))
                throw new RuntimeException("Duplicate name '"+m.getName()+"' found in mail message set");
            messages.put(m.getName(), m);
        }
    }
    
    /*
     * Get the message from the map
     * 
     * This should check for a db record before it gets it from the map
     * Look at the DB, check for the existance of the entity, if it exists, return that instead of the map value
     * 
     * If no db record, return a bean, read from the map
     * Otherwise return an entity from the db
     */
    public MailMessageDefinition getMessageDefinition(String name) throws Exception
    {
        MailMessageDefinition msg = null;
        
        try {
            msg = mailMessageDefinitionFinder.getMailMessageForName(name);
        } catch (Exception e) {
            return messages.get(name);
        }

        return msg;
    }

    public SimpleMailMessage createMailMessage(String name) throws Exception
    {
        return createMailMessage(name, null);
    }

    public SimpleMailMessage createMailMessage(String name, Map<String, String> params) throws Exception
    {
        MailMessageDefinition m = getMessageDefinition(name);
        if (m==null) throw new Exception("Mail message definition '"+name+"' not found");
        
        SimpleMailMessage msg = new SimpleMailMessage();
        String text = m.getDefaultText();
        
        if (params!=null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                text = text.replaceAll(e.getKey(), e.getValue());
            }
        }
        msg.setText(text);
        return msg;
    }

    public List<MailMessageDefinition> getMessages()
    {
        List<MailMessageDefinition> list = new ArrayList<MailMessageDefinition>(64);
        list.addAll(messages.values());
        return list;
    }
    
    public void setMailMessageDefinitionFinder(MailMessageDefinitionFinder mailMessageDefinitionFinder)
    {
        this.mailMessageDefinitionFinder = mailMessageDefinitionFinder;
    }

}

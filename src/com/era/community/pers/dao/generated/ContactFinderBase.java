package com.era.community.pers.dao.generated;

import com.era.community.pers.dao.Contact;

public interface ContactFinderBase
{
    public Contact getContactForId(int id) throws Exception;
    public Contact newContact() throws Exception;
}


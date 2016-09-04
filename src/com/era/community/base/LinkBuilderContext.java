package com.era.community.base;

import org.apache.commons.lang.*;

public class LinkBuilderContext
{    
    protected LinkBuilder toolsLinkBuilder;
    protected LinkBuilder optionsLinkBuilder;
    protected LinkBuilder dashBoardLinkBuilder;
    
    public LinkBuilderContext()
    {
    	toolsLinkBuilder = new LinkBuilder(new Link[0]);
    	optionsLinkBuilder = new LinkBuilder(new Link[0]);
    	dashBoardLinkBuilder = new LinkBuilder(new Link[0]);     
    }
    
    public void addToolLink(String text, String href)
    {
        toolsLinkBuilder.addLink(text, href, text);
    }
    public void addToolLink(String text, String href, String title)
    {
        toolsLinkBuilder.addLink(text, href, title);
    }
    public void addToolLink(String text, String href, String title, String linkClass)
    {
        toolsLinkBuilder.addLink(text, href,title, linkClass);
    }
    public void addOptionsLink(String text, String href, String title)
    {
       optionsLinkBuilder.addLink(text, href, title);
    }
    public void addDashBoardLink(String id, String text, String href, String title, String linkClass)
    {
    	dashBoardLinkBuilder.addLink(id, text, href, title, linkClass);
    }
    
    public static class LinkBuilder
    {
        private Link[] links;
        
        LinkBuilder(Link[] links)
        {
            this.links = new Link[links.length];
            
            for (int i=0; i<links.length; i++) {
                this.links[i] = new Link(links[i]);
                this.links[i].first = i==0;                         // First link
                this.links[i].authenticated = links[i].isAuthenticated();
            }
        }
        
        public void addLink(String text, String href, String title)
        {
            links = (Link[])ArrayUtils.add(links, new Link(text, href, title));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }
        
        public void addLink(String text, String href, String title, String linkClass)
        {
            links = (Link[])ArrayUtils.add(links, new Link(text, href,  title, linkClass));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }
        
        public void addLink(String id, String text, String href, String title, String linkClass)
        {
            links = (Link[])ArrayUtils.add(links, new Link(id, text, href,  title, linkClass));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }
        
        public void addLink(String text, String href, String title, String linkClass, boolean authenticated)
        {
            links = (Link[])ArrayUtils.add(links, new Link(text, href,  title, linkClass, authenticated));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }

        public void addLink(String text, String href, boolean selected, String title)
        {
            links = (Link[])ArrayUtils.add(links, new Link(text, href, selected, title));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }
        
        public void addLink(String text, String href, boolean selected, String title, boolean authenticated)
        {
            links = (Link[])ArrayUtils.add(links, new Link(text, href, selected, title, authenticated));
            for (int i=0; i<links.length; i++) this.links[i].first = i==0;
        }
        
        public int getLength()
        {
            return links.length;
        }
               
        public void selectLink(String id)
        {
            for (int i=0; i<links.length; i++) 
                links[i].selected = (id != null ? id.equals(links[i].id) : false);
        }
        
        public Link[] getLinks()
        {
            return links;
        }
        
        public void setLinks(Link[] links)
        {
            this.links = links;
        }
    }
    
    public static class Link
    {
    	String id;
        String href;
        String text ;
        String icon;
        String alt;
        String title;
        String linkClass;
        boolean selected = false;
        boolean first = false;
        boolean authenticated = false;
        
        public Link(String id, boolean authenticated, String text, String href, String title)
        {
            this.id = id;
            this.text = text;
            this.href = href;
            this.title = title;
            this.authenticated = authenticated;
        }
        
        public Link(String text, String href, String title)
        {
            this.id = "";
            this.text = text;
            this.href = href;
            this.title = title;
        }
        
        public Link(String text, String href, String title, String linkClass)
        {
            this.id = "";
            this.text = text;
            this.href = href;
            this.title = title;
            this.linkClass = linkClass;
        }
        
        public Link(String id, String text, String href, String title, String linkClass)
        {
            this.id = id;
            this.text = text;
            this.href = href;
            this.title = title;
            this.linkClass = linkClass;
        }
        
        public Link(String text, String href, String title, String linkClass, boolean authenticated)
        {
            this.id = "";
            this.text = text;
            this.href = href;
            this.title = title;
            this.linkClass = linkClass;
            this.authenticated = authenticated;
        }
        
        public Link(String text, String href, boolean selected, String title)
        {
            this.id = "";
            this.text = text;
            this.href = href;
            this.selected = selected;
            this.title = title;
        }
        
        public Link(String text, String href, boolean selected, String title, boolean authenticated)
        {
            this.id = "";
            this.text = text;
            this.href = href;
            this.selected = selected;
            this.title = title;
            this.authenticated = authenticated;
        }
        
        public Link(Link link)
        {
            this.id = link.id;
            this.href = link.href;
            this.text = link.text;
            this.selected = false;
            this.title = link.title;
            this.linkClass = link.linkClass;
        }
        
        public String getHref()
        {
            return href;
        }
        public String getId()
        {
            return id;
        }
        public boolean isSelected()
        {
            return selected;
        }
        public boolean isFirst()
        {
            return first;
        }
        public final boolean isAuthenticated()
        {
            return authenticated;
        }
        public String getText()
        {
            return text;
        }
        public final String getIcon()
        {
            return icon;
        }
        public final String getTitle()
        {
            return title;
        }
        public final void setTitle(String title)
        {
            this.title = title;
        }
        public final String getLinkClass()
        {
            return linkClass;
        }
        public final void setLinkClass(String linkClass)
        {
            this.linkClass = linkClass;
        }
    }

	public LinkBuilder getToolsLinkBuilder() {
		return toolsLinkBuilder;
	}

	public LinkBuilder getOptionsLinkBuilder() {
		return optionsLinkBuilder;
	}

	public LinkBuilder getDashBoardLinkBuilder() {
		return dashBoardLinkBuilder;
	}
}
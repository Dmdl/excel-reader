package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.themeswitcher.ThemeSwitcher;

import com.meetplanner.dto.Theme;

@ManagedBean
@SessionScoped
public class ThemeSwitcherView implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Theme> themes;
	private String theme = "south-street";

	@ManagedProperty("#{themeService}")
	private ThemeService service;

	@PostConstruct
	public void init() {
		themes = service.getThemes();
	}

	public void saveTheme(AjaxBehaviorEvent ajax) {
		setTheme((String) ((ThemeSwitcher) ajax.getSource()).getValue());
	}
	
	public List<Theme> getThemes() {
		return themes;
	}

	public void setService(ThemeService service) {
		this.service = service;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
	
}

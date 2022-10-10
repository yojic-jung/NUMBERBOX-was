package com.numberbox.mathinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class FormulKey {
	@Id
	public int id;
	
	@Column(length = 3, nullable = false)
	public int formulOrder;
	
	@Column(length = 30, nullable = false)
	public String formulName;
	
	@Column(length = 45, nullable = false)
	public String formulUi;
	
	@Column(length = 2, nullable = true)
	public String shortcutKey;
	
	@Column(length = 250, nullable = false)
	public String latexGrammer;
	
	@Column(length = 250, nullable = false)
	public String nbGrammer;
	
	@Column(length = 80, nullable = true)
	public String guide;
	
	@Column(length = 5, nullable = true)
	public String shortcutKeycode;
	
	@Column(length = 40, nullable = false)
	public String texGrammer;
	
	@Column(length = 1, nullable = false)
	public int lineChange;
	
	@Column(length = 5, nullable = false)
	public String classification;
	
	public FormulKey() { }
	
    @Builder
    public FormulKey(int id, int formulOrder, String formulName, String formulUi, String shortcutKey, String latexGrammer, String nbGrammer, String guide, String shortcutKeycode, String texGrammer, int lineChange, String classification) {
        this.id = id;
        this.formulOrder = formulOrder;
        this.formulName = formulName;
        this.formulUi = formulUi;
        this.shortcutKey = shortcutKey;
        this.latexGrammer = latexGrammer;
        this.nbGrammer = nbGrammer;
        this.guide = guide;
        this.shortcutKeycode = shortcutKeycode;
        this.texGrammer = texGrammer;
        this.lineChange = lineChange;
        this.classification = classification;
    }
	
}

package com.moonsabu.mathinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FormulKey {
	
	@Id
	public int id;
	@Column(length = 30, nullable = false)
	public String formulName;
	@Column(length = 45, nullable = false)
	public String formulUi;
	@Column(length = 2, nullable = true)
	public String shortcutKey;
	@Column(length = 40, nullable = false)
	public String grammer;
	@Column(length = 80, nullable = true)
	public String guide;
	@Column(length = 1, nullable = false)
	public String delTarget;
	
	public FormulKey() { }
	
    @Builder
    public FormulKey(int id, String formulName, String formulUi, String shortcutKey, String grammer, String guide, String delTarget) {
        this.id = id;
        this.formulName = formulName;
        this.formulUi = formulUi;
        this.shortcutKey = shortcutKey;
        this.grammer = grammer;
        this.guide = guide;
        this.delTarget = delTarget;
    }
	
}

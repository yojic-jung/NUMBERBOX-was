package com.numberbox.mathinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormulKeyDto {
	public int id;
	public int formulOrder;
	public String formulName;
	public String formulUi;
	public String shortcutKey;
	public String latexGrammer;
	public String nbGrammer;
	public String guide;
	public String shortcutKeycode;
	public String texGrammer;
	public int lineChange;
	public String classification;
}

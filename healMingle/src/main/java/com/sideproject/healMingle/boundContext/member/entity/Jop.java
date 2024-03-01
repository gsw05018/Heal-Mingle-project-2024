package com.sideproject.healMingle.boundContext.member.entity;

public enum Jop {
	PHYSIOTHERAPIST("물리치료사"),
	MANUAL_THERAPIST("도수치료사"),
	NURSE("간호사"),
	NURSING_ASSISTANT("간호조무사"),
	DOCTOR("의사");

	private final String description;

	Jop(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}

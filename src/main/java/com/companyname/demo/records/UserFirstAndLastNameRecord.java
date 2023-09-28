package com.companyname.demo.records;

//getters are automatically generated and also methods: equals(), hashCode(), and toString()
// Mapping is done by records constructor (NOTE: setters are missing!).Records are a good choice when
// you want to represent immutable data, as they are inherently immutable.
public record UserFirstAndLastNameRecord(String firstName,String lastName) { }

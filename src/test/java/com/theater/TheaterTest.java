package com.theater;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.theater.main.TheatreSeating;

public class TheaterTest {

	@Test
	public void testMain() throws IOException {
		FileReader freader = new FileReader("input.txt");
		BufferedReader br = new BufferedReader(freader);
		TheatreSeating.seating(br);
		}
	
}

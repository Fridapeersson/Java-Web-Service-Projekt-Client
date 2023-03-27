package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Scanner scan = new Scanner(System.in);
		
		//Loop som fortsätter tills användaren skriver quit
		while(true) {
			System.out.println("Skriv in ditt val");
			System.out.println("1. Hämta information om all användare");
			System.out.println("2. Hämta information om en specifik användare");
			System.out.println("Skriv quit för att avsluta");
			
			String userInput = scan.nextLine();
			if(userInput.equalsIgnoreCase("quit")) {
				break;
			}
			
			int userChoice = 0;
			try {
			userChoice = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				System.out.println("Det var inget alternativ, försök igen");
				continue;
			}
			
			String url = "http://localhost:8080/";
			String urlMod = "null";
			
			if (userChoice == 1 || userChoice == 2) {
				//Om användaren väljer 1 ändras urlMod till users
				if(userChoice == 1) {
					urlMod = "users";
				} else if (userChoice == 2) {
					System.out.println("Ange id nummer på användare (1-6): ");
					//läser in id nummer från användaren
					int chosenId = scan.nextInt();
					scan.nextLine();
					//Är id´t giltligt ändras urlMod till user?id= + chosenId
					if(chosenId >= 1 && chosenId <= 6) {
						urlMod = "user?id=" + chosenId;
					} else {
						System.out.println("Det var inte en siffra mellan 1 och 6, börja om");
						continue;
					}
				}
			} else {
				System.out.println("Välja alternativ 1 eller 2");
				continue;
			}
			
			//skapar http get request med den angivna url:en
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + urlMod)).GET().build();
			
			//Skapar http klient och skickar requesten och sparar svaret i en httpResponse
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			//Skriver ut svaret från servern
			System.out.println(response.body());
			System.out.println("------------------------------------------------------------------------");
			
		}
		scan.close();
	}

}

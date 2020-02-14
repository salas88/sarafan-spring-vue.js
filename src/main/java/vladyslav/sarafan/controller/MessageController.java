package vladyslav.sarafan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vladyslav.sarafan.exception.NotFoundException;

@RestController
@RequestMapping("message")
public class MessageController {
	
	private int counter = 4;
	private List<Map<String, String>> messages = new ArrayList<>() {{
		add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
	    add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
	    add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
	}};

	
	@GetMapping
	public List<Map<String, String>> showList() {
		return messages;
	}
	
	@GetMapping("{id}")
	public Map<String, String> showForId(@PathVariable String id){
		
		return getMessage(id);
			   
	}

	private Map<String, String> getMessage(String id) {
		return messages.stream()
			   .filter(message -> message.get("id").equals(id))
			   .findFirst()
			   .orElseThrow(NotFoundException::new);
	}
	
	@PostMapping
	public Map<String, String> addNewMessage(@RequestBody Map<String, String> message){
		message.put("id", String.valueOf(counter++));
		messages.add(message);
		return message;
		
	}
	
	@PutMapping
	public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message){
		 Map<String, String> messageFromDb = getMessage(message.get(id));
		 messageFromDb.putAll(message);
		 messageFromDb.put("id", id);
		 return messageFromDb;
	}
	
	@DeleteMapping("{id}")
	public void deleteMessage(@PathVariable String id) {
		 Map<String, String> messageFromDb = getMessage(id);
		 
	}
}

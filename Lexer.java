// An example lexical analyzer that recognizes relational operators and identifiers
// DSM, 2017

import java.io.PushbackReader;
import java.util.InputMismatchException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Lexer {
  
  // PushbackReader supports pushing characters back on the input stream
  PushbackReader reader = null;
  int lineNumber = 1;
  
  public Lexer(String filename) throws FileNotFoundException {
    
    // Create a PushbackReader that can accept up to 1 pushbacks
    this.reader = new PushbackReader(new FileReader(filename), 1);
  }
  
  /**
   * Append characters to an identified string, then compare 
   * token to specified keywords
   * @return Token the tokenized value of the input
   * @throws IOException
   */
  public Token analyzeIdentifier() throws IOException {
    
    // Collect characters in a StringBuilder
    StringBuilder identifier = new StringBuilder();
    
    while (true) {
      int c = this.reader.read();
      
      if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_') {
        reader.unread(c);
        break;
      } else {
        identifier.append((char) c);
      }
    }
    
    String identString = identifier.toString();
    
    // Check if the name's value is a keyword
    if (identString.equalsIgnoreCase("if")) {
     return new Token(TokenType.IF); 
    } else if (identString.equalsIgnoreCase("while")) {
     return new Token(TokenType.WHILE); 
    } else if (identString.equalsIgnoreCase("then")) {
     return new Token(TokenType.THEN); 
    } else if (identString.equalsIgnoreCase("do")) {
     return new Token(TokenType.DO); 
    } else if (identString.equalsIgnoreCase("and")) {
        return new Token(TokenType.AND); 
    } else if (identString.equalsIgnoreCase("endif")) {
        return new Token(TokenType.ENDIF); 
    } else if (identString.equalsIgnoreCase("endsub")) {
        return new Token(TokenType.ENDSUB); 
    } else if (identString.equalsIgnoreCase("endwhile")) {
        return new Token(TokenType.ENDWHILE); 
    } else if (identString.equalsIgnoreCase("not")) {
        return new Token(TokenType.NOT); 
    } else if (identString.equalsIgnoreCase("or")) {
        return new Token(TokenType.OR); 
    } else if (identString.equalsIgnoreCase("print")) {
        return new Token(TokenType.PRINT); 
    } else if (identString.equalsIgnoreCase("return")) {
        return new Token(TokenType.RETURN); 
    } else if (identString.equalsIgnoreCase("sub")) {
        return new Token(TokenType.SUB); 
    } else if (identString.equalsIgnoreCase("var")) {
        return new Token(TokenType.VAR); 
    } else if (identString.equalsIgnoreCase("program")) {
    	return new Token(TokenType.PROGRAM);
    } else if (identString.equalsIgnoreCase("end")) {
    	return new Token(TokenType.END);
    } else if (identString.equalsIgnoreCase("else")) {
    	return new Token(TokenType.ELSE);
    } else if (identString.equalsIgnoreCase("input")) {
    	return new Token(TokenType.INPUT);
    } else if (identString.equalsIgnoreCase("sub")) {
    	return new Token(TokenType.SUB);
    } else if (identString.equalsIgnoreCase("call")) {
    	return new Token(TokenType.CALL);
    } else if (identString.equalsIgnoreCase("for")) {
    	return new Token(TokenType.FOR);
    } else if (identString.equalsIgnoreCase("to")) {
    	return new Token (TokenType.TO);
    } else if (identString.equalsIgnoreCase("endfor")) {
    	return new Token(TokenType.ENDFOR);
    }
    else {
       return new Token(TokenType.NAME, identString);
    }
  }
  
  /**
   * Read characters in a loop, add them to the value of the
   * number being built until a non-digit character is read
   * @return Token the tokenized value of the input
   * @throws IOException
   */
  public Token analyzeNumber() throws IOException {

    int value = 0;
    
    while (true) {
      int c = this.reader.read();
      
      if (Character.isDigit(c)) {
        value = value * 10 + Character.getNumericValue(c);
      } else {
        this.reader.unread(c);
        break;
      }
    }
    
    return new Token(TokenType.NUMBER, String.valueOf(value));
  }
  
  /**
   * Reads elements from an input program and compares them against
   * a set list of valid tokens
   * @return Token the tokenized value of the input
   * @throws Exception
   */
  public Token nextToken() throws Exception {
    
    while (true) {
    
      int c = reader.read();
      
      // End-of-file
      if (c == -1) {
        return new Token(TokenType.EOF);
      }
      
      // A single = is an EQUAL token
      else if (c == '=') {
        return new Token(TokenType.EQUAL); 
      } 
      
      // Two tokens start with >
      else if (c == '>') {
        int next = this.reader.read();
        
        if (next == '=') {
          return new Token(TokenType.GREATER_THAN_OR_EQUAL); 
        } else {
          this.reader.unread(next);
          return new Token(TokenType.GREATER_THAN);
        }
      }
      
      // Three tokens start with <
      else if (c == '<') {
        int next = this.reader.read();
        
        if (next == '=') {
          return new Token(TokenType.LESS_THAN_OR_EQUAL); 
        } else if (next == '>') {
          return new Token(TokenType.NOT_EQUAL);
        } else {
          this.reader.unread(next);
          return new Token(TokenType.LESS_THAN);
        }
      }
      
      // Basic Arithmetic
      else if (c == '+') {
        return new Token(TokenType.PLUS); 
      }
      
      else if (c == '-') {
        return new Token(TokenType.MINUS);
      }
      
      else if (c == '*') {
        return new Token(TokenType.TIMES); 
      }
      
      else if (c == '/') {
        return new Token(TokenType.DIVIDE); 
      }
      
      // First character is a letter
      else if (Character.isLetter(c)) {
        
        // Push it back on the stack, then call analyzeIdentifier
        reader.unread(c);
        return this.analyzeIdentifier();
      }
      
      // First character is a digit
      else if (Character.isDigit(c)) {
        reader.unread(c);
        return this.analyzeNumber();
      }
      else if (c == 65535) {
        continue;
      }
      //Two tokens starting with :
      else if (c == ':') {
          int next = this.reader.read();
    
          if (next == '=') {
        	  return new Token(TokenType.ASSIGN);
          }
          else {
        	  this.reader.unread(next);
        	  return new Token(TokenType.COLON);
          }
        }
      else if (c == ',') {
    	  return new Token(TokenType.COMMA);
      }
      else if (c == '(') {
    	  return new Token(TokenType.LEFT_PAREN);
      }
      else if (c == ')') {
    	  return new Token(TokenType.RIGHT_PAREN);
      }
      else if (c == '%') {
    	  return new Token(TokenType.MOD);
      }
      else if (c == ',') {
    	  return new Token(TokenType.COMMA);
      }
      else if (c == '\n') {
    	  lineNumber++;
    	  return new Token(TokenType.NEWLINE);
      }
      else if (c == '"') {
    	  return new Token(TokenType.QUOTE);
      }
      //Comments
      else if (c == '{') {
    	  int next = this.reader.read(); //read the following value
    	  
    	  while (next != '}') { //until we see the closing brace
    		  next = this.reader.read(); //read the next character
    		  
    		  if (next == -1) { //reached end of file without reading closing brace
    	    	  throw new Exception (" Unrecognized token. Error on line: " + lineNumber + " Character1: " + c);
    		  }
    	  }
    	  return new Token(TokenType.COMMENT);
      }   
      //catch-all error case
      else if (!Character.isWhitespace(c)) {
    	  throw new Exception ("Unrecognized token. Error on line: " + lineNumber + " Character2: " + Character.toString((char) c));
      }
    }
  }

  public static void main(String[] args) {
    
    try {
      Lexer lex = new Lexer("src/Test/Comment.a");
      
      Token t;
      
      do {
        t = lex.nextToken();
        
        if (t.type == TokenType.NEWLINE) {
          System.out.println(); 
        } else {
          System.out.print(t  + " ");
        }
      } while (t.type != TokenType.EOF && t.type != TokenType.UNKNOWN);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
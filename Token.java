// A Token recognized by the lexical analyzer
// DSM, 2017

enum TokenType {
   NAME, NUMBER, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL,
   EQUAL, NOT_EQUAL, ASSIGN, PLUS, MINUS, TIMES, DIVIDE, EOF, IF, WHILE, THEN, DO,
   NEWLINE, UNKNOWN, AND, ENDIF, ENDSUB, ENDWHILE, NOT, OR, PRINT, SUB, VAR, RETURN, 
   COLON, COMMA, LEFT_PAREN, MOD, RIGHT_PAREN, COMMENT, INPUT, END, PROGRAM, ELSE, 
   QUOTE, CALL, FOR, TO, ENDFOR
}

public class Token {
   TokenType type;
   String value;  // Some tokens, like IDENT, have an associated value
   
   public Token(TokenType type) {
     this.type = type;
     this.value = null;
   }
   
   public Token(TokenType type, String value) {
     this.type = type;
     this.value = value;
   }
   
   public String toString() {
     if (this.type == TokenType.NAME || this.type == TokenType.NUMBER) {
       return "<" + this.type + ", " + this.value + ">";
     } else {
        return "<" + this.type + ">"; 
     }
   }
}
package main

import "strings"

var result string

func findFirstStringInBracket(str string) string {
	if len(str) > 0 {
		indexFirstBracketFound := strings.Index(str, "(")
		if indexFirstBracketFound >= 0 {
			runes := []rune(str)
			wordsAfterFirstBracket := string(runes[indexFirstBracketFound:len(str)])
			indexClosingBracketFound := strings.Index(wordsAfterFirstBracket, ")")
			if indexClosingBracketFound >= 0 {
				result = string(runes[1:indexClosingBracketFound])
			}
		}
	}
	return result
}

func main() {
	findFirstStringInBracket("(Haloo)")
	println("result", result)
}

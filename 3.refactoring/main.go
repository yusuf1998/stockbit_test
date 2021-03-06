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
				result = string(runes[1 : indexClosingBracketFound-1])
			}
		}
	}
	return result
}

func main() {
	findFirstStringInBracket("(adads)")
	println("result", result)
}

package main

import (
	"fmt"
	"sort"
	"strings"
)

var (
	tesKata = []string{"kita", "atik", "tika", "aku", "kia", "makan", "kua"}
)

func checkAnagram() {
	list := make(map[string][]string)

	for _, kata := range tesKata {
		key := sortWord(kata)
		list[key] = append(list[key], kata)
	}

	for _, kata := range list {
		fmt.Print(" [ ")
		for _, kt := range kata {
			fmt.Print(kt, " ")
		}
		fmt.Println("]")
	}
}

func sortWord(str string) string {
	s := strings.Split(str, "")
	sort.Strings(s)

	return strings.Join(s, "")
}

func main() {
	checkAnagram()
}

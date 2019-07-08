package model


type Book struct {
	Isbn string
	Author  string
	Content string
}

type BookPage struct {
	Content     []Book
	CurrentPage int
	HasNext     bool
	entries     int
}

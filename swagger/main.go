package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	_ "github.com/swaggo/swag/example/celler/httputil"
	"os"
)

// @Summary Show book info
// @Description get book by ID
// @Produce  json
// @Param isbn path int true "Book Isbn"
// @Success 200 {object} model.Book
// @Failure 400 {object} httputil.HTTPError
// @tag.name Book operations
// @Failure 404 {object} httputil.HTTPError
// @Failure 500 {object} httputil.HTTPError
// @Router /books/isbn/{isbn} [get]
func getBook(c *gin.Context) {
}

// @Summary Upload book
// @Description get book by ID
// @Produce  json
// @Accept json
// @Param bookForUpload body model.Book true "book to upload"
// @Success 204 {object} model.Book
// @tag.name Book operations
// @Failure 400 {object} httputil.HTTPError
// @Failure 404 {object} httputil.HTTPError
// @Failure 500 {object} httputil.HTTPError
// @Router /books [post]
func uploadBook(c *gin.Context) {

}

// @Summary Delete book
// @Description get book by ID
// @Produce  json
// @Param isbn path int true "Book Isbn"
// @Success 204 {string} string	""
// @tag.name Book operations
// @Failure 400 {object} httputil.HTTPError
// @Failure 404 {object} httputil.HTTPError
// @Failure 500 {object} httputil.HTTPError
// @Router /books/isbn/{isbn} [delete]
func deleteBook(c *gin.Context) {

}

// @Summary Show all books from an author
// @Description get book by ID
// @Produce  json
// @Param authorName path string true "Book author name"
// @tag.name Book operations
// @Param page path int true "current page"
// @Param size path int true "size of entries to retrieve"
// @Success 200 {object} model.BookPage
// @Failure 400 {object} httputil.HTTPError
// @Failure 404 {object} httputil.HTTPError
// @Failure 500 {object} httputil.HTTPError
// @Router /author/{authorName}/page/{page}/size/{size} [get]
func getBookPage(c *gin.Context) {
}

// @title Book Info API
// @version 1.0
// @description Api used for retrieving and adding book info
// @tag.name Book operations
// @BasePath /api/v1
// @contact.name Stefan Vassilev
// @contact.url http://www.github.com/stefanvassilev
// @contact.email stefanvassilev1@gmail.com

// @license.name Apache 2.0
// @license.url http://www.apache.org/licenses/LICENSE-2.0.html

// @termsOfService http://swagger.io/terms/
func main() {

	r := gin.New()

	cwd, _ := os.Getwd()
	fmt.Println(cwd)
	r.Static("/", cwd + "/swaggerui")
	r.Run()

}

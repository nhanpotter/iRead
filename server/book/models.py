from django.db import models
from django.contrib.auth.models import User

from .utils import check_valid_year

# Create your models here.
class Genre(models.Model):
    name = models.CharField(max_length=200)

    def __str__(self):
        return self.name
    
    def __unicode__(self):
        return self.name

class Book(models.Model):
    # Base information
    uid = models.CharField(max_length=200, primary_key=True)
    book_title = models.CharField(max_length=200)
    subject = models.CharField(max_length=200)
    summary = models.CharField(max_length=500)
    original_publisher = models.CharField(max_length=200)
    digital_publisher = models.CharField(max_length=200)
    item_format = models.CharField(max_length=200)
    language = models.CharField(max_length=200)
    item_copyright = models.CharField(max_length=200)
    author_name = models.CharField(max_length=200)
    published_year = models.CharField(max_length=200, null=True)
    resource_url = models.CharField(max_length=200)
    cover = models.CharField(max_length=200)
    thumbnail = models.CharField(max_length=200)

    # Additional Info
    genre = models.ForeignKey(Genre, on_delete=models.CASCADE)

    def __str__(self):
        return self.book_title

    def __unicode__(self):
        return self.book_title

    def clean(self):
        if self.published_year is None or not check_valid_year(self.published_year):
            self.published_year = None

    
class Comment(models.Model):
    comment = models.CharField(max_length=200)
    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.SET_NULL, null=True)

class Rating(models.Model):
    rating = models.IntegerField()
    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.SET_NULL, null=True)

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['book', 'user'], name='unique user rating')
        ]



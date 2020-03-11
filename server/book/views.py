from django.shortcuts import render
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

from .serializers import BookSerializer, CommentSerializer
from .models import *
# Create your views here.

class BookViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Book.objects.all()
    serializer_class = BookSerializer

class CommentList(APIView):
    def get(self, request, uid, format=None):
        comments = Comment.objects.filter(book__uid=uid)
        serializer = CommentSerializer(comments, many=True)
        return Response(serializer.data)

    def post(self, request, uid, format=None):
        serializer = CommentSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
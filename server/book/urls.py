from django.urls import path, include
from rest_framework.routers import DefaultRouter

from .views import BookViewSet, CommentList

router = DefaultRouter()
router.register(r'', BookViewSet)

urlpatterns = [
    path('', include(router.urls)),
    path('<uid>/comment', CommentList.as_view(), name="comment"),
]
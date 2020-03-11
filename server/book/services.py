import requests

def is_downloadable(url):
    """
    Does the url contain a downloadable resource
    """
    h = requests.head(url, allow_redirects=True)
    header = h.headers
    content_type = header.get('content-type')
    if 'text' in content_type.lower():
        return False
    if 'html' in content_type.lower():
        return False
    return True

def get_book_data():
    url = 'https://data.gov.sg/api/action/datastore_search?resource_id=835e630b-a03f-4f77-baa6-9c69c91883f2'
    get_request = requests.get(url)
    get_data = get_request.json()
    print("Success "+ str(get_data['success']))
    records = get_data['result']['records']
    print(records[0])

def get_book_file():
    url = "https://qr.nlb.sg/r/eReads?p=c2lkPWM0NDg5YTIzLWU2N2ItNGUxYi04ODY0LTAyOWEyMmFlOGYwOCZkPSUyZiUyZmVyZXNvdXJjZXMubmxiLmdvdi5zZyUyZmVyZWFkcyUyZk1vYmlsZVJlYWRzJTJmRG93bmxvYWQlMmYlM2Z1dWlkJTNkNjczZDc4MTQtOTZiOS00MjdjLWI5ZTUtOTExMzBjZDRjMjI4JTI2dGl0bGUlM2RBaXIlMmJTZWJlbGFuZ2ElMjZleHQlM2RlcHViJmR0PUVCT09LUyZkaWQ9YzQ0ODlhMjMtZTY3Yi00ZTFiLTg4NjQtMDI5YTIyYWU4ZjA4Jl9ubGI%3d"
    get_request = requests.get(url)
    print(is_downloadable(url))



get_book_file()
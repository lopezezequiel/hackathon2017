from rest_framework import api_view, status
from rest_framework import Response
from serializers import BusLineSerializer


@api_view(['POST'])
def paths(request):
    """Recibe un mensaje codificado en json, lo guarda en base de datos
    y lo retorna con el id seteado
    """

#    serializer = PathSerializer(data=request.data)

#    if not serializer.is_valid():
#        return Response(status=status.HTTP_400_BAD_REQUEST)

#    path = Path.objects.create(**serializer.validated_data)
#    path.save()

    try:
        bus_lines = BusLine.objects.all()
    except Path.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = BusLineSerializer(bus_lines, many=True)

    return Response(serializer.data)

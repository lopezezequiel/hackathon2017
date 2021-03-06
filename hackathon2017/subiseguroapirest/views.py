from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.response import Response
from subiseguroapirest.serializers import BusLineSerializer
from subiseguroapirest.models import BusLine


@api_view(['GET'])
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
    except BusLine.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = BusLineSerializer(bus_lines, many=True)

    return Response(serializer.data)

package com.example.nikita.facultapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikita.facultapplication.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.MasstransitOptions;
import com.yandex.mapkit.transport.masstransit.MasstransitRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Section;
import com.yandex.mapkit.transport.masstransit.SectionMetadata;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.mapkit.transport.masstransit.Transport;
import com.yandex.runtime.Error;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class MapFragment extends Fragment {


    private MapView mapView;
    private MapObjectCollection mapObjects;

    private final Point POINT_START_MYCITY = new Point(55.53873,37.72719);
    private final Point POINT_END_MYUNIVERCITY = new Point(55.66362,37.48309);

    private double startLat = POINT_START_MYCITY.getLatitude();
    private double startLong= POINT_START_MYCITY.getLongitude();
    private double endLat = POINT_END_MYUNIVERCITY.getLatitude();
    private double endLong = POINT_END_MYUNIVERCITY.getLongitude();
    private final Point TARGET_LOCATION = new Point((startLat + endLat) / 2 , (startLong +
            endLong) / 2);


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.mapView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // вызов карты с координатами центра москвы
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        mapObjects = mapView.getMap().getMapObjects().addCollection();

        MasstransitOptions options = new MasstransitOptions(
                new ArrayList<String>(),
                new ArrayList<String>(),
                new TimeOptions());

        // точки на карте
        List<RequestPoint> points = new ArrayList<>();
        points.add(new RequestPoint(POINT_START_MYCITY, new ArrayList<Point>(),
                RequestPointType.WAYPOINT));
        points.add(new RequestPoint(POINT_END_MYUNIVERCITY, new ArrayList<Point>(), RequestPointType
                .WAYPOINT));

        // router

      MasstransitRouter mtRouter = TransportFactory.getInstance().createMasstransitRouter();

        mtRouter.requestRoutes(points, options, new Session.RouteListener() {



            @Override
            public void onMasstransitRoutes(@NonNull List<Route> list) {
                if (list.size() > 0) {
                    for (Section section : list.get(0).getSections()) {
                        drawSection(
                                section.getMetadata().getData(),
                                SubpolylineHelper.subpolyline(
                                        list.get(0).getGeometry(), section.getGeometry()));
                    }
                }
            }

            @Override
            public void onMasstransitRoutesError(@NonNull Error error) {
                String errorMsg = getString(R.string.unknown_error_message);

                if (error instanceof RemoteError) {
                    errorMsg = getString(R.string.remote_error_message);
                } else if (error instanceof NetworkError) {
                    errorMsg = getString(R.string.network_error_message);
                }
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    //Метод отрисовки нашего пути
    private void drawSection(SectionMetadata.SectionData data, Polyline geometry) {
        // рисуем фрагмент линии, задаем цвет
        PolylineMapObject polylineMapObject = mapObjects.addPolyline(geometry);

        // выбираем один из 4 типов
        // 1 ждать общественный транспорт
        // 2 пешком
        // 3 метро
        // 4 ехать на общественном транспорте
        // проверяем на null чтобы узнать какой способ
        if (data.getTransports() != null) {
            // Поездка на участке общественного транспорта содержит информацию
            // обо всех известных транспортных линиях общественного транспорта,
            // которые могут использоваться для перемещения
            // с начала раздела до конца раздела без переноса по аналогичной геометрии
            for (Transport transport : data.getTransports()) {
                // Некоторые линии общественного транспорта могут иметь связанный с ними цвет
                // обычно подземные
                if (transport.getLine().getStyle() != null) {
                    // Цвет находится в 24-битном формате RRGGBB
                    // Преобразовываем его в 32-битный формат AARRGGBB, альфа-значение
                    // устанавливаем 255 (непрозрачный)
                    polylineMapObject.setStrokeColor(
                            transport.getLine().getStyle().getColor() | 0xFF000000 );
                    return;
                }
            }
            // линии
            // автобус - зеленая, метро - цвета веток, остальные - синяя
            HashSet<String> knownVehicleType = new HashSet<>();
            knownVehicleType.add("bus");
            knownVehicleType.add("tramway");
            for (Transport transport : data.getTransports()) {
                String sectionVehicleType = getVehicleType(transport, knownVehicleType);
                assert sectionVehicleType != null;
                if (sectionVehicleType.equals("bus")) {
                    polylineMapObject.setStrokeColor(0xFF00FF00); // зеленый
                    return;
                } else if (sectionVehicleType.equals("tramway")){
                    polylineMapObject.setStrokeColor(0xFFFF0000); // красный
                    return;
                }
            }
            polylineMapObject.setStrokeColor(0xFF0000FF); // синий
        } else {
            // это не участок общественного транспорта - черный
            polylineMapObject.setStrokeColor(0xFF000000); // черный
        }
    }

    private String getVehicleType(Transport transport, HashSet<String> knownVehicleType) {

        for (String type : transport.getLine().getVehicleTypes()) {
            if (knownVehicleType.contains(type)) {
                return type;
            }
        }
        return null;
    }
}

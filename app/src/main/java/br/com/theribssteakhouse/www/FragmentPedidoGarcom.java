package br.com.theribssteakhouse.www;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class FragmentPedidoGarcom extends Fragment {

    ListView list_pedidos;
    PedidoGarcom pedido;
    Button btn_criar_pedido;
    private final List<PedidoGarcom> pedidoGarcomList = new ArrayList<>();
    final Handler handler = new Handler();

    Runnable refresh = new Runnable() {
        @Override
        public void run() {

            pedido = new PedidoGarcom(0,"RIBS1022", "A3",new Date());
            pedidoGarcomList.add(pedido);

            PedidosGarcomAdapter adapter = new PedidosGarcomAdapter(((MainActivity)getActivity()), pedidoGarcomList);

            list_pedidos.setAdapter(adapter);

            handler.postDelayed(this, 5000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_garcom, container, false);

        btn_criar_pedido = (Button) view.findViewById(R.id.btn_criar_pedido);

        setupListPedidos(view);

        btn_criar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).backPagePedidoGarcom();
            }
        });


        return view;

    }

    private void setupListPedidos(View view) {
        list_pedidos = (ListView) view.findViewById(R.id.list_pedidos);

        list_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).detalhesPedidoGarcom(1);
            }
        });

        pedido = new PedidoGarcom(0,"RIBS1020", "A1",new Date());
        pedidoGarcomList.add(pedido);

        pedido = new PedidoGarcom(0,"RIBS1021", "A2",new Date());
        pedidoGarcomList.add(pedido);

        pedido = new PedidoGarcom(0,"RIBS1022", "A3",new Date());
        pedidoGarcomList.add(pedido);

        PedidosGarcomAdapter adapter = new PedidosGarcomAdapter(((MainActivity)getActivity()), pedidoGarcomList);

        list_pedidos.setAdapter(adapter);

        list_pedidos.post(refresh);
    }

}

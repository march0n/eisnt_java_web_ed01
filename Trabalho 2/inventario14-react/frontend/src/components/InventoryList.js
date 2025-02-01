import React, { useState, useEffect } from "react";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';

import { fetchItems, createItem, updateItem, updateQuantity, deleteItem } from "../services/api";

export default function InventoryList({ auth }) {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [displayForm, setDisplayForm] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [formData, setFormData] = useState({
    referencia: "",
    nomeProduto: "",
    preco: 0,
    descricao: "",
    quantidadeEmStock: 0,
    categoria: ""
  });
  const [displayQuantityDialog, setDisplayQuantityDialog] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [newQuantity, setNewQuantity] = useState(0);

  const loadItems = async () => {
    setLoading(true);
    try {
      const data = await fetchItems(auth);
      setItems(data);
    } catch (err) {
      console.error(err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadItems();
  }, [auth]);

  const openNew = () => {
    setEditingItem(null);
    setFormData({
      referencia: "",
      nomeProduto: "",
      preco: 0,
      descricao: "",
      quantidadeEmStock: 0,
      categoria: ""
    });
    setDisplayForm(true);
  };

  const hideDialog = () => {
    setDisplayForm(false);
  };

  const saveItem = async () => {
    try {
      if (editingItem) {
        const updated = { ...editingItem, ...formData };
        await updateItem(updated, auth);
      } else {
        await createItem(formData, auth);
      }
      setDisplayForm(false);
      loadItems();
    } catch (err) {
      console.error(err);
    }
  };

  const editItem = (item) => {
    setEditingItem(item);
    setFormData(item);
    setDisplayForm(true);
  };

  const confirmDeleteItem = async (item) => {
    if (window.confirm("Deseja remover este item?")) {
      try {
        await deleteItem(item.id, auth);
        loadItems();
      } catch (err) {
        console.error(err);
      }
    }
  };

  const openQuantityDialog = (item) => {
    setSelectedItem(item);
    setNewQuantity(item.quantidadeEmStock);
    setDisplayQuantityDialog(true);
  };

  const saveQuantity = async () => {
    try {
      await updateQuantity(selectedItem.id, newQuantity, auth);
      setDisplayQuantityDialog(false);
      loadItems();
    } catch (err) {
      console.error(err);
    }
  };

  const actionBodyTemplate = (rowData) => {
    return (
      <React.Fragment>
        {auth && auth.role === "ADMIN" && (
          <>
            <Button icon="pi pi-pencil" className="p-button-rounded p-button-success p-mr-2" onClick={() => editItem(rowData)} tooltip="Editar" />
            <Button icon="pi pi-trash" className="p-button-rounded p-button-warning p-mr-2" onClick={() => confirmDeleteItem(rowData)} tooltip="Remover" />
          </>
        )}
        {auth && (auth.role === "ADMIN" || auth.role === "USER") && (
          <Button icon="pi pi-sync" className="p-button-rounded p-button-info" onClick={() => openQuantityDialog(rowData)} tooltip="Atualizar Quantidade" />
        )}
      </React.Fragment>
    );
  };

  return (
    <div className="p-m-4">
      <h2>Inventário</h2>
      {auth && auth.role === "ADMIN" && (
        <Button label="Novo Item" icon="pi pi-plus" className="p-mb-3" onClick={openNew} />
      )}
      <DataTable value={items} paginator rows={10} loading={loading} responsiveLayout="scroll">
        <Column field="referencia" header="Referência" sortable />
        <Column field="nomeProduto" header="Nome do Produto" sortable />
        <Column field="preco" header="Preço" sortable />
        <Column field="descricao" header="Descrição" />
        <Column field="quantidadeEmStock" header="Quantidade em Stock" sortable />
        <Column field="categoria" header="Categoria" sortable />
        {auth && (auth.role === "ADMIN" || auth.role === "USER") && (
          <Column body={actionBodyTemplate} header="Ações" />
        )}
      </DataTable>

      {/* Diálogo para criar/editar item */}
      <Dialog header={editingItem ? "Editar Item" : "Novo Item"} visible={displayForm} style={{ width: '450px' }} modal onHide={hideDialog}>
        <div className="p-fluid">
          <div className="p-field">
            <label htmlFor="referencia">Referência</label>
            <InputText id="referencia" value={formData.referencia} onChange={(e) => setFormData({ ...formData, referencia: e.target.value })} />
          </div>
          <div className="p-field">
            <label htmlFor="nomeProduto">Nome do Produto</label>
            <InputText id="nomeProduto" value={formData.nomeProduto} onChange={(e) => setFormData({ ...formData, nomeProduto: e.target.value })} />
          </div>
          <div className="p-field">
            <label htmlFor="preco">Preço</label>
            <InputNumber id="preco" value={formData.preco} onValueChange={(e) => setFormData({ ...formData, preco: e.value })} mode="currency" currency="USD" locale="en-US" />
          </div>
          <div className="p-field">
            <label htmlFor="descricao">Descrição</label>
            <InputText id="descricao" value={formData.descricao} onChange={(e) => setFormData({ ...formData, descricao: e.target.value })} />
          </div>
          <div className="p-field">
            <label htmlFor="quantidadeEmStock">Quantidade em Stock</label>
            <InputNumber id="quantidadeEmStock" value={formData.quantidadeEmStock} onValueChange={(e) => setFormData({ ...formData, quantidadeEmStock: e.value })} />
          </div>
          <div className="p-field">
            <label htmlFor="categoria">Categoria</label>
            <InputText id="categoria" value={formData.categoria} onChange={(e) => setFormData({ ...formData, categoria: e.target.value })} />
          </div>
          <div className="p-field">
            <Button label="Salvar" icon="pi pi-check" onClick={saveItem} />
          </div>
        </div>
      </Dialog>

      {/* Diálogo para atualizar a quantidade */}
      <Dialog header="Atualizar Quantidade" visible={displayQuantityDialog} style={{ width: '350px' }} modal onHide={() => setDisplayQuantityDialog(false)}>
        <div className="p-field">
          <label htmlFor="quantity">Nova Quantidade</label>
          <InputNumber id="quantity" value={newQuantity} onValueChange={(e) => setNewQuantity(e.value)} />
        </div>
        <div className="p-field">
          <Button label="Salvar" icon="pi pi-check" onClick={saveQuantity} />
        </div>
      </Dialog>
    </div>
  );
}

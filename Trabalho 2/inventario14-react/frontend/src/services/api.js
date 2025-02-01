export const API_BASE_URL = "http://localhost:8080";

export function getAuthHeaders(auth) {
  if (auth && auth.username && auth.password) {
    const token = btoa(`${auth.username}:${auth.password}`);
    return {
      "Authorization": `Basic ${token}`
    };
  }
  return {};
}

export async function fetchItems(auth) {
  const response = await fetch(`${API_BASE_URL}/items`, {
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders(auth)
    }
  });
  if (!response.ok) {
    throw new Error("Erro ao buscar itens");
  }
  return await response.json();
}

export async function createItem(item, auth) {
  const response = await fetch(`${API_BASE_URL}/items`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders(auth)
    },
    body: JSON.stringify(item)
  });
  if (!response.ok) {
    throw new Error("Erro ao criar item");
  }
  return await response.json();
}

export async function updateItem(item, auth) {
  const response = await fetch(`${API_BASE_URL}/items/${item.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders(auth)
    },
    body: JSON.stringify(item)
  });
  if (!response.ok) {
    throw new Error("Erro ao atualizar item");
  }
  return await response.json();
}

export async function updateQuantity(itemId, quantidade, auth) {
  const response = await fetch(`${API_BASE_URL}/items/${itemId}/quantidade`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders(auth)
    },
    body: JSON.stringify({ quantidade })
  });
  if (!response.ok) {
    throw new Error("Erro ao atualizar quantidade");
  }
  return await response.json();
}

export async function deleteItem(itemId, auth) {
  const response = await fetch(`${API_BASE_URL}/items/${itemId}`, {
    method: "DELETE",
    headers: {
      ...getAuthHeaders(auth)
    }
  });
  if (!response.ok) {
    throw new Error("Erro ao deletar item");
  }
  return;
}

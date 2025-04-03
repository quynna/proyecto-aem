document.addEventListener("DOMContentLoaded", () => {
    const component = document.querySelector(".cmp-dynamic-search-cards");
    if (!component) return;

    const endpoint = component.dataset.endpoint;
    const container = document.getElementById("cards-container");
    const searchInput = document.getElementById("search-input");
    const filtersContainer = document.getElementById("tag-filters");
    const loadingIndicator = document.getElementById("cards-loading");

    // Función para mostrar y ocultar el loader
    const showLoader = () => {
        if (loadingIndicator) {
            loadingIndicator.style.display = "flex";
        }
    };

    const hideLoader = () => {
        if (loadingIndicator) {
            loadingIndicator.style.display = "none";
        }
    };

    console.log("📡 Llamando al endpoint:", endpoint);

    let allCards = [];

    // Función para renderizar las tarjetas en el contenedor
    const renderCards = (cards) => {
        container.innerHTML = "";
        if (!cards.length) {
            container.innerHTML = `<p>No se encontraron resultados.</p>`;
            return;
        }
        cards.forEach(card => {
            const cardEl = document.createElement("div");
            cardEl.className = "cmp-card";
            cardEl.setAttribute("role", "listitem");

            // Generar HTML para las etiquetas
            const tagsHTML = (card.tags || []).map(tag => `<span>${tag}</span>`).join("");

            cardEl.innerHTML = `
                <img src="${card.image}" alt="${card.title}" loading="lazy" />
                <h3>${card.title}</h3>
                <p>${card.description}</p>
                <div class="card-tags">${tagsHTML}</div>
            `;
            container.appendChild(cardEl);
        });
    };

    // Función de debouncing para evitar múltiples llamadas en rápido sucesión
    const debounce = (func, delay) => {
        let timeoutId;
        return (...args) => {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => {
                func.apply(null, args);
            }, delay);
        };
    };

    // Función que filtra las tarjetas en base al input de búsqueda y el tag activo
    const filterCards = () => {
        const query = (searchInput?.value || "").toLowerCase();
        // Se busca un botón activo dentro de filtros
        const activeTagEl = document.querySelector("#tag-filters .tag-active");
        const activeTag = activeTagEl ? activeTagEl.dataset.tag : "all";

        const filtered = allCards.filter(card => {
            const matchesText = card.title.toLowerCase().includes(query) ||
                card.description.toLowerCase().includes(query);
            const matchesTag = activeTag === "all" || (card.tags || []).includes(activeTag);
            return matchesText && matchesTag;
        });
        renderCards(filtered);
    };

    // Configurar los filtros a partir de las etiquetas únicas obtenidas
    const setupFilters = (tags) => {
        filtersContainer.innerHTML = "";
        const uniqueTags = Array.from(new Set(tags));
        // Se agrega la opción "all" para mostrar todas
        ["all", ...uniqueTags].forEach(tag => {
            const btn = document.createElement("button");
            btn.textContent = tag;
            btn.dataset.tag = tag;
            btn.className = tag === "all" ? "tag-active" : "";
            btn.addEventListener("click", () => {
                // Remover la clase activa de todos y agregar a la opción seleccionada
                filtersContainer.querySelectorAll("button").forEach(b => b.classList.remove("tag-active"));
                btn.classList.add("tag-active");
                filterCards();
            });
            filtersContainer.appendChild(btn);
        });
    };

    // Mostrar el loader antes de la llamada a fetch
    showLoader();

    // Realizar la llamada al endpoint
    fetch(endpoint)
        .then(res => {
            if (!res.ok) {
                throw new Error("Respuesta del servlet no válida");
            }
            return res.json();
        })
        .then(data => {
            console.log("Datos recibidos:", data);
            allCards = data;
            const allTags = allCards.flatMap(card => card.tags || []);
            setupFilters(allTags);
            renderCards(allCards);
            // Asignar el event listener con debouncing para optimizar la búsqueda
            searchInput?.addEventListener("input", debounce(filterCards, 300));
        })
        .catch(err => {
            console.error("Error al obtener datos del servlet:", err);
            container.innerHTML = `<p style="color:red;">Error al cargar tarjetas</p>`;
        })
        .finally(hideLoader);
});

package ru.froleod.FinanceTrackerApp.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-интерфейс основной
 */
@RestController
@RequestMapping("/api/finance-tracker")
public interface ICrudController<T, K> {

    @Operation(summary = "Получение всех элементов")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Элементы успешно получены"),
            @ApiResponse(responseCode = "400", description = "Сформирован некорректный запрос к сервису"),
            @ApiResponse(responseCode = "401", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса") })
    @GetMapping("/")
    List<T> getAll();

    @Operation(summary = "Получение элемента по ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Элементы успешно получены"),
            @ApiResponse(responseCode = "400", description = "Сформирован некорректный запрос к сервису"),
            @ApiResponse(responseCode = "401", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "404", description = "Элемент с данным ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса") })
    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable K id);

    @Operation(summary = "Сохранение объекта по ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Элемент сохранён успешно"),
            @ApiResponse(responseCode = "400", description = "Сформирован некорректный запрос к сервису"),
            @ApiResponse(responseCode = "401", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса") })
    @PostMapping("/save")
    T save(@Validated @RequestBody T object);

    @Operation(summary = "Удаление объекта по ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Элемент удалён успешно"),
            @ApiResponse(responseCode = "400", description = "Сформирован некорректный запрос к сервису"),
            @ApiResponse(responseCode = "401", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса") })
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable K id);

}